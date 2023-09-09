package community.mingle.mingledemo.dto.post.util

import community.mingle.mingledemo.domain.post.entity.Post
import community.mingle.mingledemo.dto.member.util.MemberDtoUtil.toDto
import community.mingle.mingledemo.dto.post.PostDto

object PostDtoUtil {

    fun Post.toDto() = PostDto(
        id = id,
        memberDto = member.toDto(),
        title = title,
        content = content,
        boardType = boardType,
        categoryType = categoryType,
        anonymous = anonymous,
        fileAttached = fileAttached,
        status = status,
        viewCount = viewCount,
        images = images,
        likes = likes,
        scraps = scraps,
        comments = comments,
        reports = reports,
        createdAt = createdAt,
        updatedAt = updatedAt,
        deletedAt = deletedAt,
    )

    fun List<Post>.toDtos(): List<PostDto> =
        this.map {
            it.toDto()
        }
}