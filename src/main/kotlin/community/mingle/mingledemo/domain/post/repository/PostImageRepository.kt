package community.mingle.mingledemo.domain.post.repository

import community.mingle.mingledemo.domain.post.entity.PostImage
import org.springframework.data.jpa.repository.JpaRepository

interface PostImageRepository : JpaRepository<PostImage, Long> {

    fun findAllByPostId(postId: Long): List<PostImage>
}