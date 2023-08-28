package community.mingle.mingledemo.domain.auth.service

import community.mingle.mingledemo.domain.auth.util.sha256
import community.mingle.mingledemo.domain.member.service.MemberService
import community.mingle.mingledemo.domain.member.service.UniversityService
import community.mingle.mingledemo.dto.member.MemberDto
import community.mingle.mingledemo.enums.MemberRole
import community.mingle.mingledemo.enums.MemberStatus
import community.mingle.mingledemo.exception.DuplicatedNicknameException
import community.mingle.mingledemo.exception.InvalidPasswordException
import community.mingle.mingledemo.exception.ReportedMemberSignUpException
import community.mingle.mingledemo.security.component.JwtHandler
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val memberService: MemberService,
    private val universityService: UniversityService,
    private val jwtHandler: JwtHandler,
){

    fun signUp(
        universityId: Int,
        email: String,
        password: String,
        nickname: String,
    ): MemberDto {
        memberService.checkDuplicatedNickName(nickname)
        memberService.validateReportedStatus(email)
        val university = universityService.getUniversityById(universityId)
        return memberService.createMember(
            university = university,
            email = email,
            password = password,
            nickname = nickname,
            role = MemberRole.USER
        )
    }

    fun login(
        email: String,
        password: String,
        fcmToken: String
    ): String {
        val memberDto = memberService.getMemberByEmail(email)
        if (memberDto.password != password.sha256()) {
            throw InvalidPasswordException()
        }

        if (memberDto.status == MemberStatus.REPORTED) {
            throw ReportedMemberSignUpException()
        }

        return jwtHandler.createAccessToken(
            memberId = memberDto.id!!,
            memberRole = memberDto.role
        )

    }
}
