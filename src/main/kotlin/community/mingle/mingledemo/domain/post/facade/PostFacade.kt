package community.mingle.mingledemo.domain.post.facade

import community.mingle.mingledemo.domain.member.service.MemberService
import community.mingle.mingledemo.domain.post.controller.response.PostsResponse
import community.mingle.mingledemo.domain.post.service.PostService
import community.mingle.mingledemo.enums.BoardType
import community.mingle.mingledemo.enums.CategoryType
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class PostFacade(
    private val memberService: MemberService,
    private val postService: PostService,
) {

    fun getPosts(
        memberId: Long,
        boardType: BoardType,
        categoryType: CategoryType,
        pageRequest: PageRequest
    ): List<PostsResponse> {
        val member = memberService.getById(memberId)
        val posts =
            if (boardType == BoardType.TOTAL) {
                postService.getTotalPosts(
                    categoryType = categoryType,
                    pageRequest = pageRequest
                )
            } else {
                postService.getUnivPosts(
                    member = member,
                    categoryType = categoryType,
                    pageRequest = pageRequest
                )
            }

        return posts.map { post ->
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
                    postImages = postImages,
                    createdAt = createdAt
                )
            }
        }
    }

}