package community.mingle.mingledemo.domain.auth.service

import community.mingle.mingledemo.domain.auth.util.sha256
import community.mingle.mingledemo.domain.member.service.MemberService
import community.mingle.mingledemo.domain.member.service.UniversityService
import community.mingle.mingledemo.dto.auth.LoginDto
import community.mingle.mingledemo.dto.member.MemberDto
import community.mingle.mingledemo.enums.MemberRole
import community.mingle.mingledemo.enums.MemberStatus
import community.mingle.mingledemo.exception.InvalidPasswordException
import community.mingle.mingledemo.exception.MemberNotFoundException
import community.mingle.mingledemo.exception.ReportedMemberLoginException
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
        fcmToken: String,
    ): MemberDto {
        memberService.checkDuplicatedNickName(nickname)
        memberService.validateReportedStatus(email)
        val university = universityService.getUniversityById(universityId)
        return memberService.createMember(
            university = university,
            email = email,
            password = password,
            nickname = nickname,
            role = MemberRole.USER,
            fcmToken = fcmToken
        )
    }

    fun login(
        email: String,
        password: String,
        fcmToken: String
    ): LoginDto {
        val memberDto = memberService.getMemberByEmailOrNull(email)?:throw MemberNotFoundException()
        if (memberDto.password != password.sha256()) {
            throw InvalidPasswordException()
        }

        if (memberDto.status == MemberStatus.REPORTED) {
            throw ReportedMemberLoginException()
        }

        val accessToken = jwtHandler.createAccessToken(
            memberId = memberDto.id!!,
            memberRole = memberDto.role
        )

        val refreshToken = jwtHandler.createRefreshToken(
            memberId = memberDto.id,
            memberRole = memberDto.role
        )

        return LoginDto(
            memberDto = memberDto,
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }

    fun reissueToken(
        refreshToken: String
    ) {

    }
}
