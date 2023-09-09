package community.mingle.mingledemo.dto.post.util

import community.mingle.mingledemo.domain.post.entity.Comment
import community.mingle.mingledemo.dto.member.util.MemberDtoUtil.toDto
import community.mingle.mingledemo.dto.post.CommentDto
import community.mingle.mingledemo.dto.post.util.PostDtoUtil.toDto

object CommentDtoUtil {

    fun Comment.toDto() = CommentDto(
        id = id!!,
        post = post.toDto(),
        member = member.toDto(),
        parentCommentId,
        mentionId,
        content,
        anonymous,
        createdAt,
        updatedAt
    )

    fun List<Comment>.toDtos() = this.map {
        it.toDto()
    }
}