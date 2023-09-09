package community.mingle.mingledemo.domain.post.controller.response

import community.mingle.mingledemo.enums.ContentStatusType
import java.time.LocalDateTime

data class GetCommentResponse(
    val id: Long,
    val content: String,
    val nicknameOrAnonymous: String,
    val status: ContentStatusType,
    val coComment: List<GetCoCommentResponse>,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)