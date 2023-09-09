package community.mingle.mingledemo.domain.post.controller

import community.mingle.mingledemo.domain.post.controller.request.CreateCommentRequest
import community.mingle.mingledemo.domain.post.controller.response.CreateCommentResponse
import community.mingle.mingledemo.domain.post.facade.CommentFacade
import community.mingle.mingledemo.security.component.TokenParser
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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
}