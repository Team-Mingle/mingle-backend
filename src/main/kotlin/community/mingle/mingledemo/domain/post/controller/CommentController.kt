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

    @Operation(
        summary = "댓글 리스트 조회"
    )
    @GetMapping("/{postId}")
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
        val (commentDetailDto, coCommentDetailDtos) = commentDtoListPair
        return with(commentDetailDto) {
            GetCommentResponse(
                id = commentDto.id,
                content = coveredContentByStatus,
                nicknameOrAnonymous = nicknameOrAnonymous,
                status = commentDto.status,
                coComment = coCommentDetailDtos.map(::mapToGetCoCommentResponse),
                createdAt = commentDto.createdAt,
                updatedAt = commentDto.updatedAt,
                isMyComment = isMyComment,
                isLiked = isLiked,
                isReport = isReport,
                isAdmin = isAdmin,
                likeCount = likeCount
            )
        }
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
                likeCount = likeCount
            )
        }
    }

    @Operation(
        summary = "댓글 삭제"
    )
    @DeleteMapping("/{commentId}")
    fun deleteComment(
        @PathVariable
        commentId: Long
    ) {
        val memberId = tokenParser.getMemberId()
        commentFacade.deleteById(
            commentId = commentId,
            memberId = memberId
        )
    }


}