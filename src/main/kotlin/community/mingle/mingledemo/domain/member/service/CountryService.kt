package community.mingle.mingledemo.domain.member.service

import community.mingle.mingledemo.domain.member.repository.CountryRepository
import community.mingle.mingledemo.dto.member.CountryDto
import community.mingle.mingledemo.dto.member.util.CountryDtoUtil.toDtos
import org.springframework.stereotype.Service

@Service
class CountryService(
    private val countryRepository: CountryRepository,
) {

    fun getAllCountries(): List<CountryDto> {
        val countries = countryRepository.findAll()
        return countries.toDtos()
    }
}