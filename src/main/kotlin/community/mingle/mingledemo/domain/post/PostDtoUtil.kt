package community.mingle.mingledemo.domain.post

import community.mingle.mingledemo.domain.post.entity.Post
import community.mingle.mingledemo.dto.post.PostDto

object PostDtoUtil {

    fun Post.toDto() = PostDto(
        id = id,
        member = member,
        title = title,
        content = content,
        board = boardType,
        category = categoryType,
        anonymous = anonymous,
        fileAttached = fileAttached,
        status = status,
        viewCount = viewCount,
    )

    fun List<Post>.toDtos(): List<PostDto> =
        this.map {
            it.toDto()
        }
}