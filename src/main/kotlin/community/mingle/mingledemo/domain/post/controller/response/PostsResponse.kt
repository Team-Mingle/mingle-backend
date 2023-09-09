package community.mingle.mingledemo.domain.post.controller.response

import java.time.LocalDateTime

data class PostsResponse(
    val postId: Long,
    val title: String,
    val content: String,
    val nickname: String,
    val likeCount: Int,
    val commentCount: Int,
    val createdAt: LocalDateTime,
    val fileAttached: Boolean,
)
