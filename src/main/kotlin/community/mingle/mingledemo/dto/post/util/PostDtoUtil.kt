package community.mingle.mingledemo.dto.post.util

import community.mingle.mingledemo.domain.post.entity.Post
import community.mingle.mingledemo.dto.member.util.MemberDtoUtil.toDto
import community.mingle.mingledemo.dto.post.PostDto
import community.mingle.mingledemo.enums.ContentStatusType

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
        nicknameOrAnonymous = CommonUtil.nicknameOrAnonymous(
            nickname = member.nickname,
            anonymous = anonymous
        )
    )

    fun List<Post>.toDtos(): List<PostDto> =
        this.map {
            it.toDto()
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

    private const val CONTENT_FOR_DELETED_STATUS = "정책 위반에 따라 운영진에 의해 삭제된 게시글입니다."
    private const val CONTENT_FOR_INACTIVE_STATUS = "삭제된 게시글입니다."
    private const val CONTENT_FOR_REPORTED_STATUS = "신고된 게시글입니다."
}