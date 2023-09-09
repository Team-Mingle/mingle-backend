package community.mingle.mingledemo.domain.post.facade

import community.mingle.mingledemo.domain.post.service.CommentService
import community.mingle.mingledemo.dto.post.CommentDto
import community.mingle.mingledemo.dto.post.util.CommentDtoUtil.toDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentFacade(
    private val commentService: CommentService,
) {

    @Transactional
    fun create(
        postId: Long,
        memberId: Long,
        parentCommentId: Long?,
        mentionId: Long?,
        content: String,
        anonymous: Boolean
    ): CommentDto {
        val comment = commentService.create(
            postId = postId,
            memberId = memberId,
            parentCommentId = parentCommentId,
            mentionId = mentionId,
            content = content,
            anonymous = anonymous
        )

        return comment.toDto()
    }
}