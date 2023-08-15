package community.mingle.mingledemo.domain.member.controller

import community.mingle.mingledemo.domain.member.controller.response.CountriesResponse
import community.mingle.mingledemo.domain.member.facade.CountryFacade
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/country")
class CountryController(
    private val countryFacade: CountryFacade,
) {
    @GetMapping("")
    fun getCountries(): CountriesResponse {
        val countriesName = countryFacade.getAllCountriesName()
        return CountriesResponse(countriesName)
    }
}