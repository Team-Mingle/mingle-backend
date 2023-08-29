package community.mingle.mingledemo.domain.auth.service

import community.mingle.mingledemo.domain.auth.util.sha256
import community.mingle.mingledemo.domain.member.service.MemberService
import community.mingle.mingledemo.domain.member.service.UniversityService
import community.mingle.mingledemo.dto.auth.LoginDto
import community.mingle.mingledemo.dto.auth.TokenDto
import community.mingle.mingledemo.dto.member.MemberDto
import community.mingle.mingledemo.enums.MemberRole
import community.mingle.mingledemo.enums.MemberStatus
import community.mingle.mingledemo.exception.InvalidPasswordException
import community.mingle.mingledemo.exception.MemberNotFoundException
import community.mingle.mingledemo.exception.ReportedMemberLoginException
import community.mingle.mingledemo.security.component.JwtHandler
import community.mingle.mingledemo.security.component.JwtVerifier
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService(
    private val memberService: MemberService,
    private val universityService: UniversityService,
    private val jwtHandler: JwtHandler,
    private val jwtVerifier: JwtVerifier,

    ) {

    @Transactional
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
        return memberService.create(
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
        val memberDto = memberService.getByEmailOrNull(email) ?: throw MemberNotFoundException()
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
    ): TokenDto {
        val verifiedRefreshToken = jwtHandler.verifyRefreshToken(refreshToken)
        val tokenDto = jwtVerifier.verifyIssuedToken(verifiedRefreshToken.refreshToken)
        val reissuedAccessToken = jwtHandler.createAccessToken(
            memberId = tokenDto.memberId,
            memberRole = tokenDto.memberRole
        )

        val reissuedRefreshToken = jwtHandler.createRefreshToken(
            memberId = tokenDto.memberId,
            memberRole = tokenDto.memberRole
        )

        return TokenDto(
            accessToken = reissuedAccessToken,
            refreshToken = reissuedRefreshToken
        )
    }
}
