package community.mingle.mingledemo.dto.post.util

import community.mingle.mingledemo.domain.member.entity.Member
import community.mingle.mingledemo.domain.post.entity.Post
import community.mingle.mingledemo.dto.member.util.MemberDtoUtil.toDto
import community.mingle.mingledemo.dto.post.PostDetailDto
import community.mingle.mingledemo.dto.post.PostDto
import community.mingle.mingledemo.dto.post.PostPreviewDto
import community.mingle.mingledemo.dto.post.util.CommonUtil.nicknameOrAnonymous
import community.mingle.mingledemo.enums.ContentStatusType
import community.mingle.mingledemo.enums.MemberRole

object PostDtoUtil {

    fun Post.toDto() = PostDto(
        id = id,
        memberDto = member.toDto(),
        title = title,
        content = coverContentByStatus(
            content = content,
            status = status
        ),
        boardType = boardType,
        categoryType = categoryType,
        anonymous = anonymous,
        fileAttached = fileAttached,
        status = status,
        viewCount = viewCount,
        images = images,
        likes = likes,
        scraps = scraps,
        comments = comments,
        reports = reports,
        createdAt = createdAt,
        updatedAt = updatedAt,
        deletedAt = deletedAt,
        nicknameOrAnonymous = nicknameOrAnonymous(
            nickname = member.nickname,
            anonymous = anonymous
        )
    )

    fun List<Post>.toDtos(): List<PostDto> =
        this.map {
            it.toDto()
        }

    fun Post.toDetailDto() = PostDetailDto(
        postDto = this.toDto(),
        isMyPost = isMyPost(
            member = member,
            post = this
        ),
        isLiked = isLiked(
            member = member,
            post = this
        ),
        isScraped = isScraped(
            member = member,
            post = this
        ),
        isReport = isReport(
            member = member,
            post = this
        ),
        isAdmin = isAdmin(
            post = this
        ),
        likeCount = likes.size,
        commentCount = comments.size,
        scrapCount = scraps.size
    )

    fun List<Post>.toDetailDtos() = this.map { post ->
        post.toDetailDto()
    }

    fun Post.toPreviewDto() =
        with(this) {
            PostPreviewDto(
                postId = id!!,
                title = title,
                content = content,
                nicknameOrAnonymous = nicknameOrAnonymous(
                    nickname = member.nickname,
                    anonymous = anonymous
                ),
                likeCount = likes.size,
                commentCount = comments.size,
                createdAt = createdAt,
                fileAttached = fileAttached,
            )
        }

    fun List<Post>.toPreviewDtos() = this.map { post ->
        post.toPreviewDto()
    }


    private fun coverContentByStatus(
        content: String,
        status: ContentStatusType
    ): String =
        when (status) {
            ContentStatusType.DELETED ->
                CONTENT_FOR_DELETED_STATUS

            ContentStatusType.INACTIVE ->
                CONTENT_FOR_INACTIVE_STATUS

            ContentStatusType.REPORTED ->
                CONTENT_FOR_REPORTED_STATUS

            else ->
                content
        }

    private fun isMyPost(
        member: Member,
        post: Post
    ): Boolean = post.member == member

    private fun isLiked(
        member: Member,
        post: Post
    ): Boolean =
        post.likes.any { postLike ->
            postLike.member == member
        }

    private fun isScraped(
        member: Member,
        post: Post
    ): Boolean =
        post.scraps.any { postScrap ->
            postScrap.member == member
        }

    private fun isAdmin(
        post: Post
    ): Boolean =
        post.member.role == MemberRole.ADMIN

    private fun isReport(
        member: Member,
        post: Post
    ): Boolean = post.reports.any { postReport ->
        postReport.reporterMember == member
    }

    private const val CONTENT_FOR_DELETED_STATUS = "정책 위반에 따라 운영진에 의해 삭제된 게시글입니다."
    private const val CONTENT_FOR_INACTIVE_STATUS = "삭제된 게시글입니다."
    private const val CONTENT_FOR_REPORTED_STATUS = "신고된 게시글입니다."
}