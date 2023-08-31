package community.mingle.mingledemo.domain.post.repository

import community.mingle.mingledemo.domain.post.entity.Post
import community.mingle.mingledemo.enums.CategoryType
import community.mingle.mingledemo.exception.PostNotFoundException
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
interface PostRepository : JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p WHERE p.categoryType = :categoryType AND p.boardType = 'UNIV' AND p.member.university.id = :universityId")
    fun findAllByCategoryTypeAndBoardTypeIsUnivAndMemberUniversityId(
        categoryType: CategoryType,
        universityId: Int,
        pageable: Pageable
    ): Slice<Post>

    @Query("SELECT p FROM Post p WHERE p.categoryType = :categoryType AND p.boardType = 'TOTAL'")
    fun findAllByCategoryTypeAndBoardTypeIsTotal(
        categoryType: CategoryType,
        pageable: Pageable
    ): Slice<Post>

    companion object {
        fun PostRepository.find(id: Long) = findByIdOrNull(id)
            ?: throw PostNotFoundException()
    }
}