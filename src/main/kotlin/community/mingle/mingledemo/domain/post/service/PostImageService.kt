package community.mingle.mingledemo.domain.post.service

import community.mingle.mingledemo.domain.post.entity.Post
import community.mingle.mingledemo.domain.post.entity.PostImage
import community.mingle.mingledemo.domain.post.repository.PostImageRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
class PostImageService(
    private val postImageRepository: PostImageRepository,
) {

    @Transactional
    fun create(
        post: Post,
        images: List<MultipartFile>
    ): MutableList<PostImage> {
        val postImages = images.map {
            PostImage(
                post = post,
                url = "ToBeDeveloped"
            )
        }
        return postImageRepository.saveAll(postImages)
    }

    @Transactional
    fun update(
        post: Post,
        imageIdsToDelete: List<Long>?,
        imagesToAdd: List<MultipartFile>?
    ) {
        if (imageIdsToDelete != null) {
            postImageRepository.deleteAllById(imageIdsToDelete)
        }

        if (imagesToAdd != null) {
            create(
                post = post,
                images = imagesToAdd
            )
        }
    }

    @Transactional
    fun delete(
        postId: Long
    ) {
        val postImages = postImageRepository.findAllByPostId(postId)
        postImageRepository.deleteAll(postImages)
    }


}