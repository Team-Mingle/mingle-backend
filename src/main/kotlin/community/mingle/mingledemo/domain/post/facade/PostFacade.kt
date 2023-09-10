package community.mingle.mingledemo.domain.post.facade

import community.mingle.mingledemo.domain.member.service.MemberService
import community.mingle.mingledemo.domain.post.service.PostImageService
import community.mingle.mingledemo.domain.post.service.PostService
import community.mingle.mingledemo.dto.post.PostDetailDto
import community.mingle.mingledemo.dto.post.PostDto
import community.mingle.mingledemo.dto.post.PostPreviewDto
import community.mingle.mingledemo.dto.post.util.PostDtoUtil.isMyPost
import community.mingle.mingledemo.dto.post.util.PostDtoUtil.toDetailDto
import community.mingle.mingledemo.dto.post.util.PostDtoUtil.toDto
import community.mingle.mingledemo.dto.post.util.PostDtoUtil.toPreviewDtos
import community.mingle.mingledemo.enums.BoardType
import community.mingle.mingledemo.enums.CategoryType
import community.mingle.mingledemo.exception.post.InvalidPostAccessException
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
class PostFacade(
    private val postService: PostService,
    private val postImageService: PostImageService,
    private val memberService: MemberService,
) {
    @Transactional
    fun create(
        memberId: Long,
        title: String,
        content: String,
        boardType: BoardType,
        categoryType: CategoryType,
        anonymous: Boolean,
        images: List<MultipartFile>?
    ): PostDto {
        val fileAttached = !images.isNullOrEmpty()
        val post = postService.create(
            memberId = memberId,
            title = title,
            content = content,
            boardType = boardType,
            categoryType = categoryType,
            anonymous = anonymous,
            fileAttached = fileAttached
        )

        if (fileAttached) {
            postImageService.create(
                post = post,
                images = images!!
            )
        }
        return post.toDto()
    }

    fun pagePosts(
        memberId: Long,
        boardType: BoardType,
        categoryType: CategoryType,
        pageRequest: PageRequest
    ): List<PostPreviewDto> =
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
        }.toPreviewDtos()

    fun getById(
        memberId: Long,
        postId: Long
    ): PostDetailDto {
        if (!hasAccessRight(
                memberId = memberId,
                postId = postId
            )
        ) throw InvalidPostAccessException()

        val post = postService.getById(postId)
        postService.updateViewCount(postId)

        return post.toDetailDto()
    }

    @Transactional
    fun update(
        memberId: Long,
        postId: Long,
        title: String,
        content: String,
        anonymous: Boolean,
        imageIdsToDelete: List<Long>?,
        imagesToAdd: List<MultipartFile>?
    ): PostDto {
        if (!hasAccessRight(
                memberId = memberId,
                postId = postId
            )
        ) throw InvalidPostAccessException()
        val post = postService.update(
            postId = postId,
            title = title,
            content = content,
            anonymous = anonymous
        )
        postImageService.update(
            post = post,
            imageIdsToDelete = imageIdsToDelete,
            imagesToAdd = imagesToAdd
        )
        return post.toDto()
    }

    @Transactional
    fun delete(
        postId: Long,
        memberId: Long,
    ) {
        val post = postService.getById(postId)
        val member = memberService.getById(memberId)
        if (!isMyPost(
                post = post,
                member = member
            )
        ) throw InvalidPostAccessException()

        postService.delete(postId)
        postImageService.delete(postId)
    }

    fun hasAccessRight(
        memberId: Long,
        postId: Long
    ): Boolean {
        val member = memberService.getById(memberId)
        val post = postService.getById(postId)

        return if (post.boardType == BoardType.TOTAL) {
            member.university.country == post.member.university.country
        } else member.university == post.member.university
    }


    companion object {
        private const val ANONYMOUS_NICKNAME = "익명"
    }

}