package community.mingle.mingledemo.domain.member.repository

import community.mingle.mingledemo.domain.member.entity.University
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UniversityRepository : JpaRepository<University, Int> {

    fun findAllByCountryCountry(country: String): List<University>
}