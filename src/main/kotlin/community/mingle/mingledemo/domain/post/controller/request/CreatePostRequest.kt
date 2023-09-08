package community.mingle.mingledemo.domain.post.controller.request

import community.mingle.mingledemo.enums.BoardType
import community.mingle.mingledemo.enums.CategoryType
import org.springframework.web.multipart.MultipartFile
import java.io.Serializable

data class CreatePostRequest(
    val title: String,
    val content: String,
    val boardType: BoardType,
    val categoryType: CategoryType,
    val anonymous: Boolean,
    val images: List<MultipartFile>
) : Serializable
