package community.mingle.mingledemo.domain.member.controller

import community.mingle.mingledemo.domain.member.controller.response.CountriesResponse
import community.mingle.mingledemo.domain.member.facade.CountryFacade
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/country")
class CountryController(
    private val countryFacade: CountryFacade,
) {
    @Operation(
        summary = "회원가입 화면에서 전체 국가 리스트 조회",
        responses = [
            ApiResponse(responseCode = "200", description = "OK"),
        ],
    )
    @GetMapping("")
    fun getCountries(): CountriesResponse {
        val countriesName = countryFacade.getAllCountriesName()
        return CountriesResponse(countriesName)
    }
}