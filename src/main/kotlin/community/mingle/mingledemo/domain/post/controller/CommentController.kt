package community.mingle.mingledemo.domain.post.controller

import community.mingle.mingledemo.domain.post.controller.request.CreateCommentRequest
import community.mingle.mingledemo.domain.post.controller.response.CreateCommentResponse
import community.mingle.mingledemo.domain.post.controller.response.GetCoCommentResponse
import community.mingle.mingledemo.domain.post.controller.response.GetCommentResponse
import community.mingle.mingledemo.domain.post.facade.CommentFacade
import community.mingle.mingledemo.dto.post.CommentDto
import community.mingle.mingledemo.security.component.TokenParser
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/comment")
class CommentController(
    private val tokenParser: TokenParser,
    private val commentFacade: CommentFacade,
) {

    @Operation(
        summary = "댓글 생성"
    )
    @PostMapping
    fun createComment(
        @RequestBody
        @Valid
        createCommentRequest: CreateCommentRequest
    ): CreateCommentResponse {
        val memberId = tokenParser.getMemberId()
        val commentDto = with(createCommentRequest) {
            commentFacade.create(
                postId = postId,
                memberId = memberId,
                parentCommentId = parentCommentId,
                mentionId = mentionId,
                content = content,
                anonymous = anonymous
            )
        }

        return CreateCommentResponse(
            commentId = commentDto.id
        )
    }

    @PostMapping("/{postId}")
    fun getComments(
        @PathVariable
        postId: Long
    ): List<GetCommentResponse> {
        val memberId = tokenParser.getMemberId()
        val pairCommentsToCoCommentsByPostId = commentFacade.pairCommentsToCoCommentsByPostId(
            postId = postId,
            memberId = memberId
        )

        return pairCommentsToCoCommentsByPostId.map(::mapToGetCommentResponse)

    }

    private fun mapToGetCommentResponse(commentDtoListPair: Pair<CommentDto, List<CommentDto>>): GetCommentResponse {
        val (comment, coComments) = commentDtoListPair
        return GetCommentResponse(
            id = comment.id,
            content = comment.content,
            nicknameOrAnonymous = comment.nicknameOrAnonymous,
            status = comment.status,
            coComment = coComments.map(::mapToGetCoCommentResponse),
            createdAt = comment.createdAt,
            updatedAt = comment.updatedAt
        )
    }

    private fun mapToGetCoCommentResponse(coComment: CommentDto): GetCoCommentResponse {
        return GetCoCommentResponse(
            id = coComment.id,
            content = coComment.content,
            nicknameOrAnonymous = coComment.nicknameOrAnonymous,
            status = coComment.status,
            createdAt = coComment.createdAt,
            updatedAt = coComment.updatedAt
        )
    }


}