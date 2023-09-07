package community.mingle.mingledemo.dto.post

import java.time.LocalDateTime

data class PostPreviewDto(
    val title: String,
    val content: String,
    val nicknameOrAnonymous: String,
    val likeCount: Int,
    val commentCount: Int,
    val createdAt: LocalDateTime,
    val fileAttached: Boolean,
)
