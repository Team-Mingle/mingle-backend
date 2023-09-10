package community.mingle.mingledemo.domain.post.repository

import community.mingle.mingledemo.domain.post.entity.Comment
import community.mingle.mingledemo.exception.comment.CommentNotFoundException
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository : JpaRepository<Comment, Long> {

    fun findAllByPostId(postId: Long): List<Comment>

    companion object {
        fun CommentRepository.find(commentId: Long) = findByIdOrNull(commentId) ?: throw CommentNotFoundException()
    }
}