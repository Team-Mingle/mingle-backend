package community.mingle.mingledemo.domain.post.controller.response

import community.mingle.mingledemo.enums.BoardType
import community.mingle.mingledemo.enums.CategoryType
import java.io.Serializable

data class UpdatePostResponse (
    val postId: Long,
    val title: String,
    val content: String,
    val boardType: BoardType,
    val categoryType: CategoryType,
    val anonymous: Boolean
): Serializable