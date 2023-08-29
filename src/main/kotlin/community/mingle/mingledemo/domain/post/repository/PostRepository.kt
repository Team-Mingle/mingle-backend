package community.mingle.mingledemo.domain.post.repository

import community.mingle.mingledemo.domain.post.entity.Post
import community.mingle.mingledemo.enums.BoardType
import community.mingle.mingledemo.enums.CategoryType
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PostRepository : JpaRepository<Post, Long> {

    fun findAllByCategoryTypeAndBoardTypeAndMemberUniversityId(
        categoryType: CategoryType,
        boardType: BoardType,
        universityId: Int,
        pageable: Pageable
    ): Slice<Post>
}