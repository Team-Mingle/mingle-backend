package community.mingle.mingledemo.domain.post.facade

import community.mingle.mingledemo.domain.post.service.CommentService
import community.mingle.mingledemo.dto.post.CommentDto
import community.mingle.mingledemo.dto.post.util.CommentDtoUtil.toDto
import community.mingle.mingledemo.exception.InvalidPostAccess
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentFacade(
    private val commentService: CommentService,
    private val postFacade: PostFacade,
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
        if (!postFacade.hasAccessRight(
                memberId = memberId,
                postId = postId
            )
        ) throw InvalidPostAccess()
        
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