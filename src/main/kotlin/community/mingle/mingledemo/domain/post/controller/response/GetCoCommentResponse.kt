package community.mingle.mingledemo.domain.post.controller.response

import community.mingle.mingledemo.enums.ContentStatusType
import java.time.LocalDateTime

data class GetCoCommentResponse(
    val id: Long,
    val content: String,
    val nicknameOrAnonymous: String,
    val status: ContentStatusType,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val isMyComment: Boolean,
    val isLiked: Boolean,
    val isReport: Boolean,
    val isAdmin: Boolean,
    val likeCount: Int
)
