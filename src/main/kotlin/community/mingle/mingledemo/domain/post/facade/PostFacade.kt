package community.mingle.mingledemo.domain.post.facade

import community.mingle.mingledemo.domain.member.entity.Member
import community.mingle.mingledemo.domain.member.service.MemberService
import community.mingle.mingledemo.domain.post.PostDtoUtil.toDto
import community.mingle.mingledemo.domain.post.entity.Post
import community.mingle.mingledemo.domain.post.service.PostService
import community.mingle.mingledemo.dto.post.PostDetailDto
import community.mingle.mingledemo.dto.post.PostDto
import community.mingle.mingledemo.dto.post.PostPreviewDto
import community.mingle.mingledemo.enums.BoardType
import community.mingle.mingledemo.enums.CategoryType
import community.mingle.mingledemo.enums.MemberRole
import community.mingle.mingledemo.exception.InvalidPostAccess
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class PostFacade(
    private val postService: PostService,
    private val memberService: MemberService,
) {
    fun create(
        memberId: Long,
        title: String,
        content: String,
        boardType: BoardType,
        categoryType: CategoryType,
        anonymous: Boolean,
    ): PostDto {
        val post = postService.create(
            memberId = memberId,
            title = title,
            content = content,
            boardType = boardType,
            categoryType = categoryType,
            anonymous = anonymous,
        )

        return post.toDto()
    }

    fun pagePosts(
        memberId: Long,
        boardType: BoardType,
        categoryType: CategoryType,
        pageRequest: PageRequest
    ): List<PostPreviewDto> =
        if (boardType == BoardType.TOTAL) {
            postService.getTotalPostsByCategory(
                categoryType = categoryType,
                pageRequest = pageRequest
            )
        } else {
            postService.getUnivPostsByCategory(
                memberId = memberId,
                categoryType = categoryType,
                pageRequest = pageRequest
            )
        }.map { post ->
            val nicknameOrAnonymous = nicknameOrAnonymous(post)
            PostPreviewDto(
                title = post.title,
                content = post.content,
                nicknameOrAnonymous = nicknameOrAnonymous,
                likeCount = post.likes.size,
                commentCount = post.scraps.size,
                createdAt = post.createdAt,
                fileAttached = post.fileAttached,
            )
        }

    fun getById(
        memberId: Long,
        postId: Long
    ): PostDetailDto {
        val member = memberService.getById(memberId)
        val post = postService.getById(postId)
        if (!hasReadAccess(member = member, post = post))
            throw InvalidPostAccess()

        val nicknameOrAnonymous = nicknameOrAnonymous(
            post = post
        )
        val isMyPost = isMyPost(
            post = post,
            member = member
        )
        val isLiked = isLiked(
            post = post,
            member = member
        )
        val isScraped = isScraped(
            post = post,
            member = member
        )
        val isReported = isReport(
            post = post,
            member = member,
        )
        val isAdmin = isAdmin(post)

        return PostDetailDto(
            postDto = post.toDto(),
            nicknameOrAnonymous = nicknameOrAnonymous,
            isMyPost = isMyPost,
            isLiked = isLiked,
            isScraped = isScraped,
            isAdmin = isAdmin,
            isReported = isReported
        )
    }

    private fun hasReadAccess(
        member: Member,
        post: Post
    ): Boolean {
        return if (post.boardType == BoardType.TOTAL) {
            member.university.country == post.member.university.country
        } else member.university == post.member.university
    }

    private fun nicknameOrAnonymous(
        post: Post
    ): String {
        return if (post.anonymous) {
            ANONYMOUS_NICKNAME
        } else post.member.nickname
    }

    private fun isMyPost(
        post: Post,
        member: Member
    ) = post.member == member

    private fun isLiked(
        post: Post,
        member: Member
    ) = post.likes.any { postLike ->
        postLike.member == member
    }

    private fun isScraped(
        post: Post,
        member: Member
    ) = post.scraps.any { postScrap ->
        postScrap.member == member
    }

    private fun isAdmin(
        post: Post
    ) = post.member.role == MemberRole.ADMIN

    private fun isReport(
        post: Post,
        member: Member
    ) = post.reports.any { postReport ->
        postReport.reporterMember == member
    }

    companion object {
        private const val ANONYMOUS_NICKNAME = "익명"
    }

}