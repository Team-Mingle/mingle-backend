package community.mingle.mingledemo.domain.member.repository

import community.mingle.mingledemo.domain.member.entity.University
import community.mingle.mingledemo.exception.member.UniversityNotFoundException
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
interface UniversityRepository : JpaRepository<University, Int> {

    fun findAllByCountryCountry(country: String): List<University>

    companion object {
        fun UniversityRepository.find(universityId: Int): University =
            findByIdOrNull(universityId) ?: throw UniversityNotFoundException()
    }
}