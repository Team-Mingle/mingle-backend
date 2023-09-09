package community.mingle.mingledemo.domain.post.repository

import community.mingle.mingledemo.domain.post.entity.Comment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository : JpaRepository<Comment, Long>