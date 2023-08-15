package community.mingle.mingledemo.domain.member.repository

import community.mingle.mingledemo.domain.member.entity.Country
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface CountryRepository : JpaRepository<Country, String>