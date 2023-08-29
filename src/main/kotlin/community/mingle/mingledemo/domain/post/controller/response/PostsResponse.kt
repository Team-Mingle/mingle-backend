package community.mingle.mingledemo.domain.post.controller.response

import community.mingle.mingledemo.domain.post.entity.PostImage
import community.mingle.mingledemo.enums.BoardType
import community.mingle.mingledemo.enums.CategoryType
import community.mingle.mingledemo.enums.ContentStatusType
import java.time.LocalDateTime

data class PostsResponse(
    val memberId: Long,
    val title: String,
    val content: String,
    val board: BoardType,
    val category: CategoryType,
    val anonymous: Boolean,
    val fileAttached: Boolean,
    val status: ContentStatusType,
    val viewCount: Int,
    val postLikeCount: Int,
    val postScrapCount: Int,
    val postImages: List<PostImage>,
    val createdAt: LocalDateTime,
)
