package community.mingle.mingledemo.dto.member

import community.mingle.mingledemo.domain.member.entity.University

data class CountryDto(
    var country: String,
    var universities: MutableList<University> = mutableListOf<University>(),
)
