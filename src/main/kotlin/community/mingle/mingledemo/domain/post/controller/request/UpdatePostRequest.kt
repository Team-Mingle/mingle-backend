package community.mingle.mingledemo.domain.post.controller.request

import org.springframework.web.multipart.MultipartFile

data class UpdatePostRequest(
    val title: String,
    val content: String,
    val anonymous: Boolean,
    val imageIdsToDelete: List<Long>?,
    val imagesToAdd: List<MultipartFile>?
)