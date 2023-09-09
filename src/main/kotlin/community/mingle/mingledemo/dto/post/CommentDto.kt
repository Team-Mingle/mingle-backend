package community.mingle.mingledemo.dto.post

import community.mingle.mingledemo.dto.member.MemberDto
import java.time.LocalDateTime

data class CommentDto(
    val id: Long,
    val post: PostDto,
    val member: MemberDto,
    val parentCommentId: Long?,
    val mentionId: Long?,
    val content: String,
    val anonymous: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)
