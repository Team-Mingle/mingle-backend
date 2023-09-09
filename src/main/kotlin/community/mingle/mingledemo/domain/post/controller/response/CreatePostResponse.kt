package community.mingle.mingledemo.domain.post.controller.response

import java.io.Serializable

data class CreatePostResponse(
    val postId: Long,
) : Serializable
