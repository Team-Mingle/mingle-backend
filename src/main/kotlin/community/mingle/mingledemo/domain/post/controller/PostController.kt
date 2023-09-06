package community.mingle.mingledemo.domain.post.controller

import community.mingle.mingledemo.domain.post.controller.request.CreatePostRequest
import community.mingle.mingledemo.domain.post.controller.response.CreatePostResponse
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
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/post")
class PostController(
    private val tokenParser: TokenParser,
    private val postFacade: PostFacade,
) {

    @PostMapping
    fun createPost(
        @RequestBody
        createPostRequest: CreatePostRequest,
    ): CreatePostResponse {
        val memberId = tokenParser.getMemberId()
        val post = with(createPostRequest) {
             postFacade.create(
                memberId = memberId,
                title = title,
                content = content,
                boardType = boardType,
                categoryType = categoryType,
                anonymous = anonymous,
            )
        }

        return with(post) {
            CreatePostResponse(
                    postId = id!!,
                    title = title,
                    content = content,
                    boardType = boardType,
                    categoryType = categoryType,
                    anonymous = anonymous
            )
        }
    }

    @GetMapping
    fun pagePosts(
        @RequestParam
        boardType: BoardType,
        @RequestParam
        categoryType: CategoryType,
        @Parameter
        pageable: Pageable
    ): List<PostsResponse> {
        val memberId = tokenParser.getMemberId()

        val pagePosts = postFacade.pagePosts(
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

        return pagePosts.map { post ->
            with(post) {
                PostsResponse(
                        memberId = post.member.id!!,
                        title = title,
                        content = content,
                        board = this.boardType,
                        category = this.categoryType,
                        anonymous = anonymous,
                        fileAttached = fileAttached,
                        status = status,
                        viewCount = viewCount,
                        postLikeCount = postLikes.size,
                        postScrapCount = postScraps.size,
                        createdAt = createdAt
                )
            }
        }
    }
}