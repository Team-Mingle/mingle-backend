package community.mingle.mingledemo.domain.post.controller.response

import java.time.LocalDateTime

data class GetPostDetailResponse(
    val id: Long,
    val nickname: String,
    val title: String,
    val content: String,
    val likeCount: Int,
    val scrapCount: Int,
    val commentCount: Int,
    val isMyPost: Boolean,
    val isLiked: Boolean,
    val isScraped: Boolean,
    val isReported: Boolean,
    val isAdmin: Boolean,
    val fileAttached: Boolean,
    val viewCount: Int,
    val createdAt: LocalDateTime,
    val imagesUrl: List<String>,
)
