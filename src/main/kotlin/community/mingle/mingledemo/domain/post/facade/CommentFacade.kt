package community.mingle.mingledemo.domain.post.facade

import community.mingle.mingledemo.domain.member.service.MemberService
import community.mingle.mingledemo.domain.post.entity.Comment
import community.mingle.mingledemo.domain.post.service.CommentService
import community.mingle.mingledemo.dto.post.CommentDetailDto
import community.mingle.mingledemo.dto.post.CommentDto
import community.mingle.mingledemo.dto.post.util.CommentDtoUtil.isMyComment
import community.mingle.mingledemo.dto.post.util.CommentDtoUtil.toDetailDto
import community.mingle.mingledemo.dto.post.util.CommentDtoUtil.toDetailDtos
import community.mingle.mingledemo.dto.post.util.CommentDtoUtil.toDto
import community.mingle.mingledemo.enums.ContentStatusType
import community.mingle.mingledemo.exception.comment.InvalidCommentAccessException
import community.mingle.mingledemo.exception.post.InvalidPostAccessException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentFacade(
    private val commentService: CommentService,
    private val memberService: MemberService,
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
        ) throw InvalidPostAccessException()

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
    ): List<Pair<CommentDetailDto, List<CommentDetailDto>>> {
        if (!postFacade.hasAccessRight(
                memberId = memberId, postId = postId
            )
        ) throw InvalidPostAccessException()

        val mapCommentToCoComment = commentService.mapCommentsByPostId(postId)
        val filteredMapCommentToCoComment = filterComments(mapCommentToCoComment)

        return filteredMapCommentToCoComment.map { (parentComment, coComments) ->
            Pair(parentComment.toDetailDto(), coComments.toDetailDtos())
        }
    }

    @Transactional
    fun deleteById(
        commentId: Long,
        memberId: Long
    ) {
        val comment = commentService.getById(commentId)
        val member = memberService.getById(memberId)
        if (!isMyComment(
                comment = comment,
                member = member
            )
        ) throw InvalidCommentAccessException()

        commentService.deleteById(commentId)


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