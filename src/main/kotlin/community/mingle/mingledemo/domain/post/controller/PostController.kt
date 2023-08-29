package community.mingle.mingledemo.domain.post.controller

import community.mingle.mingledemo.domain.post.controller.response.PostsResponse
import community.mingle.mingledemo.domain.post.facade.PostFacade
import community.mingle.mingledemo.enums.BoardType
import community.mingle.mingledemo.enums.CategoryType
import community.mingle.mingledemo.security.component.TokenParser
import io.swagger.v3.oas.annotations.Parameter
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/post")
class PostController(
    private val tokenParser: TokenParser,
    private val postFacade: PostFacade,
) {

    @GetMapping
    fun getPosts(
        @RequestParam
        boardType: BoardType,
        @RequestParam
        categoryType: CategoryType,
        @Parameter
        pageable: Pageable
    ): List<PostsResponse> {
        val memberId = tokenParser.getMemberId()
        return postFacade.getPosts(
            memberId = memberId,
            boardType = boardType,
            categoryType = categoryType,
            pageRequest = PageRequest.of(
                pageable.pageNumber,
                pageable.pageSize,
                Sort.Direction.DESC,
                "createdAt"
            )
        )
    }
}