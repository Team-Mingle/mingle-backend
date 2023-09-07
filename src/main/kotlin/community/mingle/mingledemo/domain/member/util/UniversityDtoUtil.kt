package community.mingle.mingledemo.domain.member.util

import community.mingle.mingledemo.domain.member.entity.University
import community.mingle.mingledemo.domain.member.util.CountryDtoUtil.toDto
import community.mingle.mingledemo.dto.member.UniversityDto

object UniversityDtoUtil {

    fun University.toDto(): UniversityDto =
        UniversityDto(
            id = id,
            emailDomain = emailDomain,
            name = name,
            countryDto = country.toDto(),
            members = members,
        )

    fun List<University>.toDtos(): List<UniversityDto> =
        this.map { it.toDto() }
}