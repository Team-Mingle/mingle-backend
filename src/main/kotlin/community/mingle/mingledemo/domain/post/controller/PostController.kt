package community.mingle.mingledemo.domain.post.controller

import community.mingle.mingledemo.domain.post.controller.request.CreatePostRequest
import community.mingle.mingledemo.domain.post.controller.response.CreatePostResponse
import community.mingle.mingledemo.domain.post.controller.response.GetPostDetailResponse
import community.mingle.mingledemo.domain.post.controller.response.PostsResponse
import community.mingle.mingledemo.domain.post.facade.PostFacade
import community.mingle.mingledemo.enums.BoardType
import community.mingle.mingledemo.enums.CategoryType
import community.mingle.mingledemo.security.component.TokenParser
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/post")
class PostController(
    private val tokenParser: TokenParser,
    private val postFacade: PostFacade,
) {

    @Operation(
            summary = "게시물 생성",
    )
    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun createPost(
        @ModelAttribute
        createPostRequest: CreatePostRequest,
    ): CreatePostResponse {
        val memberId = tokenParser.getMemberId()
        val postDto = with(createPostRequest) {
            postFacade.create(
                memberId = memberId,
                title = title,
                content = content,
                boardType = boardType,
                categoryType = categoryType,
                anonymous = anonymous,
                images = images
            )
        }

        return with(postDto) {
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

    @Operation(
            summary = "게시물 리스트 조회"
    )
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

        val postPreviewDtos = postFacade.pagePosts(
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

        return postPreviewDtos.map { post ->
            with(post) {
                PostsResponse(
                    title = title,
                    content = content,
                    nickname = nicknameOrAnonymous,
                    fileAttached = fileAttached,
                    likeCount = likeCount,
                    commentCount = commentCount,
                    createdAt = createdAt
                )
            }
        }
    }

    @Operation(
            summary = "게시물 상세 조회"
    )
    @GetMapping("/{postId}")
    fun getPostDetail(
        @PathVariable
        postId: Long
    ): GetPostDetailResponse {
        val memberId = tokenParser.getMemberId()
        val postDetailDto = postFacade.getById(
            memberId = memberId,
            postId = postId
        )
        return with(postDetailDto) {
            GetPostDetailResponse(
                id = postId,
                nickname = nicknameOrAnonymous,
                title = postDto.title,
                content = postDto.content,
                viewCount = postDto.viewCount,
                fileAttached = postDto.fileAttached,
                likeCount = postDto.likes.size,
                scrapCount = postDto.scraps.size,
                commentCount = postDto.comments.size,
                isMyPost = isMyPost,
                isLiked = isLiked,
                isScraped = isScraped,
                isReported = isReported,
                isAdmin = isAdmin,
                createdAt = postDto.createdAt,
                imagesUrl = postDto.images.map { it.url }
            )
        }
    }
}