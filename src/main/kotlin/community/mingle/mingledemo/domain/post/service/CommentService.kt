package community.mingle.mingledemo.domain.post.service

import community.mingle.mingledemo.domain.member.repository.MemberRepository
import community.mingle.mingledemo.domain.member.repository.MemberRepository.Companion.find
import community.mingle.mingledemo.domain.post.entity.Comment
import community.mingle.mingledemo.domain.post.repository.CommentRepository
import community.mingle.mingledemo.domain.post.repository.PostRepository
import community.mingle.mingledemo.domain.post.repository.PostRepository.Companion.find
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentService(
    private val commentRepository: CommentRepository,
    private val postRepository: PostRepository,
    private val memberRepository: MemberRepository,
) {

    @Transactional
    fun create(
        postId: Long,
        memberId: Long,
        parentCommentId: Long?,
        mentionId: Long?,
        content: String,
        anonymous: Boolean
    ): Comment {
        val post = postRepository.find(postId)
        val member = memberRepository.find(memberId)

        return Comment(
            post = post,
            member = member,
            parentCommentId = parentCommentId,
            mentionId = mentionId,
            content = content,
            anonymous = anonymous
        ).run { commentRepository.save(this) }
    }
}