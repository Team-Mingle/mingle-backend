package community.mingle.mingledemo.domain.post.service

import community.mingle.mingledemo.domain.member.repository.MemberRepository
import community.mingle.mingledemo.domain.member.repository.MemberRepository.Companion.find
import community.mingle.mingledemo.domain.post.entity.Post
import community.mingle.mingledemo.domain.post.entity.PostImage
import community.mingle.mingledemo.domain.post.repository.PostRepository
import community.mingle.mingledemo.domain.post.repository.PostRepository.Companion.find
import community.mingle.mingledemo.enums.BoardType
import community.mingle.mingledemo.enums.CategoryType
import community.mingle.mingledemo.enums.ContentStatusType
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class PostService(
    private val postRepository: PostRepository,
    private val memberRepository: MemberRepository,
) {

    fun createPost(
        memberId: Long,
        title: String,
        content: String,
        boardType: BoardType,
        categoryType: CategoryType,
        anonymous: Boolean,
    ): Post {
        val member = memberRepository.find(memberId)

        return Post(
            member = member,
            title = title,
            content = content,
            boardType = boardType,
            categoryType = categoryType,
            anonymous = anonymous,
            fileAttached = false,
        ).run { postRepository.save(this) }
    }

    fun getUnivPosts(
        memberId: Long,
        categoryType: CategoryType,
        pageRequest: PageRequest
    ): MutableList<Post> {
        val member = memberRepository.find(memberId)
        val posts = postRepository.findAllByCategoryTypeAndBoardTypeIsUnivAndMemberUniversityId(
            categoryType = categoryType,
            universityId = member.university.id!!,
            pageable = pageRequest
        )
        return posts.content
    }

    fun getTotalPosts(
        categoryType: CategoryType,
        pageRequest: PageRequest
    ): MutableList<Post> {
        val posts = postRepository.findAllByCategoryTypeAndBoardTypeIsTotal(
            categoryType = categoryType,
            pageable = pageRequest
        )
        return posts.content
    }

    fun createPostImage(
        postId: Long,
        url: String,
    ): PostImage {
        val post = postRepository.find(postId)
        return PostImage(
            post = post,
            url = url
        )
    }

}