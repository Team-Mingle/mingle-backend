package community.mingle.mingledemo.domain.post.facade

import community.mingle.mingledemo.domain.post.entity.Comment
import community.mingle.mingledemo.domain.post.service.CommentService
import community.mingle.mingledemo.dto.post.CommentDto
import community.mingle.mingledemo.dto.post.util.CommentDtoUtil.toDto
import community.mingle.mingledemo.dto.post.util.CommentDtoUtil.toDtos
import community.mingle.mingledemo.enums.ContentStatusType
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
                memberId = memberId, postId = postId
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

    fun pairCommentsToCoCommentsByPostId(
        postId: Long,
        memberId: Long,
    ): List<Pair<CommentDto, List<CommentDto>>> {
        if (!postFacade.hasAccessRight(
                memberId = memberId, postId = postId
            )
        ) throw InvalidPostAccess()

        val mapCommentToCoComment = commentService.mapCommentsByPostId(postId)
        val filteredMapCommentToCoComment = filterComments(mapCommentToCoComment)

        return filteredMapCommentToCoComment.map { (parentComment, coComments) ->
            Pair(parentComment.toDto(), coComments.toDtos())
        }

    }

    fun filterComments(
        mapCommentToCoComment: Map<Comment, List<Comment>>
    ): Map<Comment, List<Comment>> {
        return mapCommentToCoComment.filter { (parentComment, coComments) ->
            !(parentComment.status == ContentStatusType.INACTIVE &&
                    (coComments.isEmpty() || coComments.all { it.status == ContentStatusType.INACTIVE }))
        }.mapValues { (_, coComments) ->
            coComments.filter {
                it.status != ContentStatusType.INACTIVE
            }
        }
    }
}