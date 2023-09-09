package community.mingle.mingledemo.domain.post.controller.request

import jakarta.validation.constraints.NotEmpty

data class CreateCommentRequest(
    val postId: Long,
    val parentCommentId: Long?,
    val mentionId: Long?,

    @field:NotEmpty
    val content: String,
    val anonymous: Boolean
)
