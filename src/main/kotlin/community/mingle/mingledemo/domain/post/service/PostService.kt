package community.mingle.mingledemo.domain.post.service

import community.mingle.mingledemo.domain.member.repository.MemberRepository
import community.mingle.mingledemo.domain.member.repository.MemberRepository.Companion.find
import community.mingle.mingledemo.domain.post.entity.Post
import community.mingle.mingledemo.domain.post.repository.PostImageRepository
import community.mingle.mingledemo.domain.post.repository.PostRepository
import community.mingle.mingledemo.domain.post.repository.PostRepository.Companion.find
import community.mingle.mingledemo.enums.BoardType
import community.mingle.mingledemo.enums.CategoryType
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostService(
    private val postRepository: PostRepository,
    private val postImageRepository: PostImageRepository,
    private val memberRepository: MemberRepository,
) {

    @Transactional
    fun create(
        memberId: Long,
        title: String,
        content: String,
        boardType: BoardType,
        categoryType: CategoryType,
        anonymous: Boolean,
        fileAttached: Boolean
    ): Post {
        val member = memberRepository.find(memberId)



        return Post(
            member = member,
            title = title,
            content = content,
            boardType = boardType,
            categoryType = categoryType,
            anonymous = anonymous,
            fileAttached = fileAttached,
        ).run { postRepository.save(this) }
    }

    fun getUnivPostsByCategory(
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

    fun getTotalPostsByCategory(
        categoryType: CategoryType,
        pageRequest: PageRequest
    ): MutableList<Post> {
        val posts = postRepository.findAllByCategoryTypeAndBoardTypeIsTotal(
            categoryType = categoryType,
            pageable = pageRequest
        )
        return posts.content
    }

    fun getById(
        postId: Long
    ) = postRepository.find(postId)

    @Transactional
    fun update(
        postId: Long,
        title: String,
        content: String,
        anonymous: Boolean,
    ): Post {
        val post = postRepository.find(postId)
        return post.apply {
            this.title = title
            this.content = content
            this.anonymous = anonymous
        }
    }

    @Transactional
    fun delete(
        postId: Long
    ) = postRepository.deleteById(postId)

}