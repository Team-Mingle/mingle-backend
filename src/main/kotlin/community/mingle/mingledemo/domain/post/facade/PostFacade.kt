package community.mingle.mingledemo.domain.post.facade

import community.mingle.mingledemo.domain.member.service.MemberService
import community.mingle.mingledemo.domain.post.service.PostImageService
import community.mingle.mingledemo.domain.post.service.PostService
import community.mingle.mingledemo.dto.post.PostDetailDto
import community.mingle.mingledemo.dto.post.PostDto
import community.mingle.mingledemo.dto.post.PostPreviewDto
import community.mingle.mingledemo.dto.post.util.CommonUtil.nicknameOrAnonymous
import community.mingle.mingledemo.dto.post.util.PostDtoUtil.toDto
import community.mingle.mingledemo.enums.BoardType
import community.mingle.mingledemo.enums.CategoryType
import community.mingle.mingledemo.enums.MemberRole
import community.mingle.mingledemo.exception.InvalidPostAccess
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
class PostFacade(
    private val postService: PostService,
    private val postImageService: PostImageService,
    private val memberService: MemberService,
) {
    @Transactional
    fun create(
        memberId: Long,
        title: String,
        content: String,
        boardType: BoardType,
        categoryType: CategoryType,
        anonymous: Boolean,
        images: List<MultipartFile>?
    ): PostDto {
        val fileAttached = !images.isNullOrEmpty()
        val post = postService.create(
            memberId = memberId,
            title = title,
            content = content,
            boardType = boardType,
            categoryType = categoryType,
            anonymous = anonymous,
            fileAttached = fileAttached
        )

        if (fileAttached) {
            postImageService.create(
                post = post,
                images = images!!
            )
        }
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
            PostPreviewDto(
                postId = post.id!!,
                title = post.title,
                content = post.content,
                nicknameOrAnonymous = nicknameOrAnonymous(
                    nickname = post.member.nickname,
                    anonymous = post.anonymous
                ),
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
        if (!hasAccessRight(
                memberId = memberId,
                postId = postId
            )
        ) throw InvalidPostAccess()

        val isMyPost = isMyPost(
            postId = postId,
            memberId = memberId,
        )
        val isLiked = isLiked(
            postId = postId,
            memberId = memberId,
        )
        val isScraped = isScraped(
            postId = postId,
            memberId = memberId,
        )
        val isReported = isReport(
            postId = postId,
            memberId = memberId,
        )
        val isAdmin = isAdmin(postId)

        val post = postService.getById(postId)
        postService.updateViewCount(postId)

        return PostDetailDto(
            postDto = post.toDto(),
            isMyPost = isMyPost,
            isLiked = isLiked,
            isScraped = isScraped,
            isAdmin = isAdmin,
            isReported = isReported
        )
    }

    @Transactional
    fun update(
        memberId: Long,
        postId: Long,
        title: String,
        content: String,
        anonymous: Boolean,
        imageIdsToDelete: List<Long>?,
        imagesToAdd: List<MultipartFile>?
    ): PostDto {
        if (!hasAccessRight(
                memberId = memberId,
                postId = postId
            )
        ) throw InvalidPostAccess()
        val post = postService.update(
            postId = postId,
            title = title,
            content = content,
            anonymous = anonymous
        )
        postImageService.update(
            post = post,
            imageIdsToDelete = imageIdsToDelete,
            imagesToAdd = imagesToAdd
        )
        return post.toDto()
    }

    @Transactional
    fun delete(
        postId: Long
    ) {
        postService.delete(postId)
        postImageService.delete(postId)
    }

    fun hasAccessRight(
        memberId: Long,
        postId: Long
    ): Boolean {
        val member = memberService.getById(memberId)
        val post = postService.getById(postId)

        return if (post.boardType == BoardType.TOTAL) {
            member.university.country == post.member.university.country
        } else member.university == post.member.university
    }

    private fun isMyPost(
        memberId: Long,
        postId: Long
    ): Boolean {
        val member = memberService.getById(memberId)
        val post = postService.getById(postId)

        return post.member == member
    }

    private fun isLiked(
        memberId: Long,
        postId: Long
    ): Boolean {
        val member = memberService.getById(memberId)
        val post = postService.getById(postId)

        return post.likes.any { postLike ->
            postLike.member == member
        }
    }

    private fun isScraped(
        memberId: Long,
        postId: Long
    ): Boolean {
        val member = memberService.getById(memberId)
        val post = postService.getById(postId)

        return post.scraps.any { postScrap ->
            postScrap.member == member
        }
    }

    private fun isAdmin(
        postId: Long
    ): Boolean {
        val post = postService.getById(postId)
        return post.member.role == MemberRole.ADMIN
    }

    private fun isReport(
        memberId: Long,
        postId: Long
    ): Boolean {
        val member = memberService.getById(memberId)
        val post = postService.getById(postId)

        return post.reports.any { postReport ->
            postReport.reporterMember == member
        }
    }

    companion object {
        private const val ANONYMOUS_NICKNAME = "익명"
    }

}