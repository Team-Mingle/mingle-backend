package community.mingle.mingledemo.domain.member.facade

import community.mingle.mingledemo.domain.member.service.CountryService
import org.springframework.stereotype.Service

@Service
class CountryFacade(
    private val countryService: CountryService,
) {
    fun getAllCountriesName(): List<String> {
        val countryDtos = countryService.getAllCountries()
        return countryDtos.map { it.country }
    }
}