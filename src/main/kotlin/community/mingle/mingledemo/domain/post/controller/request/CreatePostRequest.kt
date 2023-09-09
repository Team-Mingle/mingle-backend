package community.mingle.mingledemo.domain.post.controller.request

import community.mingle.mingledemo.enums.BoardType
import community.mingle.mingledemo.enums.CategoryType
import jakarta.validation.constraints.NotEmpty
import org.springframework.web.multipart.MultipartFile
import java.io.Serializable

data class CreatePostRequest(
    @field:NotEmpty
    val title: String,
    @field:NotEmpty
    val content: String,
    val boardType: BoardType,
    val categoryType: CategoryType,
    val anonymous: Boolean,
    val images: List<MultipartFile>?
) : Serializable
