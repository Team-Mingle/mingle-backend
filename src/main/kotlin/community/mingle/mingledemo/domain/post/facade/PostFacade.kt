package community.mingle.mingledemo.domain.post.facade

import community.mingle.mingledemo.domain.post.PostDtoUtil.toDto
import community.mingle.mingledemo.domain.post.PostDtoUtil.toDtos
import community.mingle.mingledemo.domain.post.controller.response.CreatePostResponse
import community.mingle.mingledemo.domain.post.controller.response.PostsResponse
import community.mingle.mingledemo.domain.post.entity.Post
import community.mingle.mingledemo.domain.post.service.PostService
import community.mingle.mingledemo.dto.post.PostDto
import community.mingle.mingledemo.enums.BoardType
import community.mingle.mingledemo.enums.CategoryType
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class PostFacade(
    private val postService: PostService,
) {
    fun create(
        memberId: Long,
        title: String,
        content: String,
        boardType: BoardType,
        categoryType: CategoryType,
        anonymous: Boolean,
    ): PostDto {
        val post = postService.create(
                memberId = memberId,
                title = title,
                content = content,
                boardType = boardType,
                categoryType = categoryType,
                anonymous = anonymous,
        )

        return post.toDto()
    }

    fun pagePosts(
        memberId: Long,
        boardType: BoardType,
        categoryType: CategoryType,
        pageRequest: PageRequest
    ): List<PostDto> =
        if (boardType == BoardType.TOTAL) {
            postService.getTotalPostsByCategory(
                categoryType = categoryType,
                pageRequest = pageRequest
            )
        } else {
            postService.getUnivPostsByCategory(
                memberId = memberId,
                categoryType = categoryType,
                pageRequest = pageRequest
            )
        }.toDtos()

}