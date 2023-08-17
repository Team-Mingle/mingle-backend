package community.mingle.mingledemo.domain.member.controller

import community.mingle.mingledemo.domain.member.controller.response.EmailDomainResponse
import community.mingle.mingledemo.domain.member.controller.response.UniversitiesResponse
import community.mingle.mingledemo.domain.member.service.UniversityService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/university")
class UniversityController(
    private val universityService: UniversityService,
) {

    @Operation(
        summary = "회원가입 화면에서 국가에 따른 대학 리스트 조회",
        responses = [
            ApiResponse(responseCode = "200", description = "OK"),
        ],
    )
    @GetMapping("/{countryName}")
    fun getUniversities(
        @PathVariable
        countryName: String,
    ): List<UniversitiesResponse> {
        val universityDtos = universityService.getUniversitiesByCountry(countryName)
        val universitiesResponses = universityDtos.map {
            UniversitiesResponse(
                id = it.id,
                name = it.name,
                emailDomain = it.emailDomain
            )
        }
        return universitiesResponses
    }

    @GetMapping("/email/{universityId}")
    fun getUniversityEmailDomains(
        @PathVariable
        universityId: Int
    ): EmailDomainResponse {
        val emailDomain = universityService.getUniversityEmailDomainById(universityId)
        return EmailDomainResponse(emailDomain)
    }
}