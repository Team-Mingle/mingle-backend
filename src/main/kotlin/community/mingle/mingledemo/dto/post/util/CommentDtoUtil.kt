package community.mingle.mingledemo.dto.post.util

import community.mingle.mingledemo.domain.post.entity.Comment
import community.mingle.mingledemo.dto.member.util.MemberDtoUtil.toDto
import community.mingle.mingledemo.dto.post.CommentDto
import community.mingle.mingledemo.dto.post.util.CommonUtil.nicknameOrAnonymous
import community.mingle.mingledemo.dto.post.util.PostDtoUtil.toDto
import community.mingle.mingledemo.enums.ContentStatusType

object CommentDtoUtil {

    fun Comment.toDto() = CommentDto(
        id = id!!,
        post = post.toDto(),
        member = member.toDto(),
        parentCommentId = parentCommentId,
        mentionId = mentionId,
        content = coverContentByStatus(
            content = content,
            status = status
        ),
        anonymous = anonymous,
        createdAt = createdAt,
        updatedAt = updatedAt,
        status = status,
        nicknameOrAnonymous = nicknameOrAnonymous(
            nickname = member.nickname,
            anonymous = anonymous
        )
    )

    fun List<Comment>.toDtos() = this.map {
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

    private const val CONTENT_FOR_DELETED_STATUS = "정책 위반에 따라 운영진에 의해 삭제된 댓글입니다."
    private const val CONTENT_FOR_INACTIVE_STATUS = "삭제된 댓글입니다."
    private const val CONTENT_FOR_REPORTED_STATUS = "신고된 댓글입니다."
}