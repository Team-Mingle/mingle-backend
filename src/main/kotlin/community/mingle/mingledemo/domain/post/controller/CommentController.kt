package community.mingle.mingledemo.domain.post.controller

import community.mingle.mingledemo.domain.post.controller.request.CreateCommentRequest
import community.mingle.mingledemo.domain.post.controller.response.CreateCommentResponse
import community.mingle.mingledemo.domain.post.controller.response.GetCoCommentResponse
import community.mingle.mingledemo.domain.post.controller.response.GetCommentResponse
import community.mingle.mingledemo.domain.post.facade.CommentFacade
import community.mingle.mingledemo.dto.post.CommentDetailDto
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

    private fun mapToGetCommentResponse(commentDtoListPair: Pair<CommentDetailDto, List<CommentDetailDto>>): GetCommentResponse {
        val (commentDetailDto, commentDetailDtos) = commentDtoListPair
        return GetCommentResponse(
            id = commentDetailDto.commentDto.id,
            content = commentDetailDto.coveredContentByStatus,
            nicknameOrAnonymous = commentDetailDto.nicknameOrAnonymous,
            status = commentDetailDto.commentDto.status,
            coComment = commentDetailDtos.map(::mapToGetCoCommentResponse),
            createdAt = commentDetailDto.commentDto.createdAt,
            updatedAt = commentDetailDto.commentDto.updatedAt,
            isMyComment = commentDetailDto.isMyComment,
            isLiked = commentDetailDto.isLiked,
            isReport = commentDetailDto.isReport,
            isAdmin = commentDetailDto.isAdmin
        )
    }

    private fun mapToGetCoCommentResponse(coCommentDetailDto: CommentDetailDto): GetCoCommentResponse {
        return with(coCommentDetailDto) {
            GetCoCommentResponse(
                id = commentDto.id,
                content = coveredContentByStatus,
                nicknameOrAnonymous = nicknameOrAnonymous,
                status = commentDto.status,
                createdAt = commentDto.createdAt,
                updatedAt = commentDto.updatedAt,
                isMyComment = isMyComment,
                isLiked = isLiked,
                isReport = isReport,
                isAdmin = isAdmin,
            )
        }
    }


}