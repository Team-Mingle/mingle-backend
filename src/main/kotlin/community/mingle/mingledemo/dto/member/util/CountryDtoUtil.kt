package community.mingle.mingledemo.dto.member.util

import community.mingle.mingledemo.domain.member.entity.Country
import community.mingle.mingledemo.dto.member.CountryDto

object CountryDtoUtil {
    fun Country.toDto(): CountryDto =
        CountryDto(
            country = country,
            universities = universities
        )

    fun List<Country>.toDtos(): List<CountryDto> =
        this.map { it.toDto() }

}