package community.mingle.mingledemo.dto.post

import community.mingle.mingledemo.dto.member.MemberDto
import community.mingle.mingledemo.enums.ContentStatusType
import java.time.LocalDateTime

data class CommentDto(
    val id: Long,
    val post: PostDto,
    val member: MemberDto,
    val parentCommentId: Long?,
    val mentionId: Long?,
    val content: String,
    val anonymous: Boolean,
    val status: ContentStatusType,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val nicknameOrAnonymous: String,
)
