package community.mingle.mingledemo.domain.member.service

import community.mingle.mingledemo.domain.member.repository.UniversityRepository
import community.mingle.mingledemo.domain.member.repository.UniversityRepository.Companion.find
import community.mingle.mingledemo.domain.member.util.UniversityDtoUtil.toDtos
import community.mingle.mingledemo.dto.member.UniversityDto
import org.springframework.stereotype.Service

@Service
class UniversityService(
    private val universityRepository: UniversityRepository,
) {

    fun getUniversityById(universityId: Int) = universityRepository.find(universityId)

    fun getUniversitiesByCountry(
        countryName: String
    ): List<UniversityDto> {
        val universities = universityRepository.findAllByCountryCountry(countryName)
        return universities.toDtos()
    }

    fun getUniversityEmailDomainById(
        universityId: Int
    ): String {
        val university = universityRepository.find(universityId)
        return university.emailDomain
    }
}