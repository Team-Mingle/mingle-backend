package community.mingle.mingledemo.dto.member

import community.mingle.mingledemo.domain.member.entity.Member
import java.io.Serializable

data class UniversityDto(
    val id: Int?,
    val emailDomain: String,
    val name: String,
    val countryDto: CountryDto,
    val members: List<Member>
) : Serializable
