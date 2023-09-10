package community.mingle.mingledemo.dto.post.util

import community.mingle.mingledemo.domain.member.entity.Member
import community.mingle.mingledemo.domain.post.entity.Comment
import community.mingle.mingledemo.dto.member.util.MemberDtoUtil.toDto
import community.mingle.mingledemo.dto.post.CommentDetailDto
import community.mingle.mingledemo.dto.post.CommentDto
import community.mingle.mingledemo.dto.post.util.CommonUtil.nicknameOrAnonymous
import community.mingle.mingledemo.dto.post.util.PostDtoUtil.toDto
import community.mingle.mingledemo.enums.ContentStatusType
import community.mingle.mingledemo.enums.MemberRole

object CommentDtoUtil {

    fun Comment.toDto() = CommentDto(
        id = id!!,
        post = post.toDto(),
        member = member.toDto(),
        parentCommentId = parentCommentId,
        mentionId = mentionId,
        content = content,
        anonymous = anonymous,
        createdAt = createdAt,
        updatedAt = updatedAt,
        status = status,
        likes = likes,
        reports = reports
    )

    fun List<Comment>.toDtos() = this.map {
        it.toDto()
    }

    fun Comment.toDetailDto() = CommentDetailDto(
        commentDto = this.toDto(),
        isMyComment = isMyComment(
            member = member,
            comment = this,
        ),
        isLiked = isLiked(
            member = member,
            comment = this,
        ),
        isReport = isReport(
            member = member,
            comment = this,
        ),
        isAdmin = isAdmin(this),
        nicknameOrAnonymous = nicknameOrAnonymous(
            nickname = member.nickname,
            anonymous = anonymous,
        ),
        coveredContentByStatus = coverContentByStatus(
            content = content,
            status = status
        ),
        likeCount = likes.size
    )

    fun List<Comment>.toDetailDtos() = this.map {
        it.toDetailDto()
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

    fun isMyComment(
        member: Member,
        comment: Comment
    ) = comment.member == member

    private fun isLiked(
        member: Member,
        comment: Comment
    ) = comment.likes.any { commentLike ->
        commentLike.member == member
    }

    private fun isAdmin(
        comment: Comment
    ) = comment.member.role == MemberRole.ADMIN

    private fun isReport(
        member: Member,
        comment: Comment,
    ) = comment.reports.any { commentReport ->
        commentReport.reporterMember == member
    }

    private const val CONTENT_FOR_DELETED_STATUS = "정책 위반에 따라 운영진에 의해 삭제된 댓글입니다."
    private const val CONTENT_FOR_INACTIVE_STATUS = "삭제된 댓글입니다."
    private const val CONTENT_FOR_REPORTED_STATUS = "신고된 댓글입니다."
}