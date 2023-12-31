package community.mingle.mingledemo.domain.auth.service

import community.mingle.mingledemo.domain.auth.util.sha256
import community.mingle.mingledemo.domain.member.entity.Member
import community.mingle.mingledemo.domain.member.service.MemberService
import community.mingle.mingledemo.domain.member.service.UniversityService
import community.mingle.mingledemo.dto.auth.LoginDto
import community.mingle.mingledemo.dto.auth.TokenDto
import community.mingle.mingledemo.enums.MemberRole
import community.mingle.mingledemo.enums.MemberStatus
import community.mingle.mingledemo.exception.auth.InvalidPasswordException
import community.mingle.mingledemo.exception.auth.ReportedMemberLoginException
import community.mingle.mingledemo.exception.member.MemberNotFoundException
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
    ): Member {
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
        val member = memberService.getByEmailOrNull(email) ?: throw MemberNotFoundException()
        if (member.password != password.sha256()) {
            throw InvalidPasswordException()
        }

        if (member.status == MemberStatus.REPORTED) {
            throw ReportedMemberLoginException()
        }

        val accessToken = jwtHandler.createAccessToken(
            memberId = member.id!!,
            memberRole = member.role
        )

        val refreshToken = jwtHandler.createRefreshToken(
            memberId = member.id,
            memberRole = member.role
        )

        return LoginDto(
            member = member,
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
