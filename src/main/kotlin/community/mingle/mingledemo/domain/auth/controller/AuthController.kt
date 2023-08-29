package community.mingle.mingledemo.domain.auth.controller

import community.mingle.mingledemo.domain.auth.controller.request.EmailRequest
import community.mingle.mingledemo.domain.auth.controller.request.LoginRequest
import community.mingle.mingledemo.domain.auth.controller.request.SignUpRequest
import community.mingle.mingledemo.domain.auth.controller.response.ReissueTokenResponse
import community.mingle.mingledemo.domain.auth.controller.response.SignUpOrLoginResponse
import community.mingle.mingledemo.domain.auth.service.AuthService
import community.mingle.mingledemo.domain.member.service.MemberService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController(
    private val memberService: MemberService,
    private val authService: AuthService,
) {

    @Operation(
        summary = "회원가입 화면에서 이메일 형식 및 중복 검증",
    )
    @PostMapping("/check-email")
    fun verifyEmail(
        @RequestBody
        @Valid
        emailRequest: EmailRequest
    ): Boolean {
        val email = emailRequest.email
        memberService.checkMemberExistedByEmail(email)
        return true
    }

    @Operation(
        summary = "회원가입",
    )
    @PostMapping("/sign-up")
    fun signUp(
        @RequestBody
        @Valid
        signUpRequest: SignUpRequest
    ): SignUpOrLoginResponse {
        with(signUpRequest) {
            authService.signUp(
                universityId = universityId,
                email = email,
                password = password,
                nickname = nickname,
                fcmToken = fcmToken
            )

            val loginDto = authService.login(
                email = email,
                password = password,
                fcmToken = fcmToken,
            )

            return SignUpOrLoginResponse(
                memberId = loginDto.memberDto.id!!,
                email = loginDto.memberDto.email,
                nickname = loginDto.memberDto.nickname,
                universityName = loginDto.memberDto.university.name,
                accessToken = loginDto.accessToken,
                refreshToken = loginDto.refreshToken,
            )
        }

    }

    @Operation(
        summary = "로그인",
    )
    @PostMapping("/login")
    fun login(
        @RequestBody
        @Valid
        loginRequest: LoginRequest
    ): SignUpOrLoginResponse {
        val loginDto = authService.login(
            email = loginRequest.email,
            password = loginRequest.password,
            fcmToken = loginRequest.fcmToken,
        )
        return SignUpOrLoginResponse(
            memberId = loginDto.memberDto.id!!,
            email = loginDto.memberDto.email,
            nickname = loginDto.memberDto.nickname,
            universityName = loginDto.memberDto.university.name,
            accessToken = loginDto.accessToken,
            refreshToken = loginDto.refreshToken,
        )
    }

    @Operation(
        summary = "토큰 재발급",
    )
    @Parameter(name = "Authorization", required = true, description = "RefreshToken", `in` = ParameterIn.HEADER)
    @PostMapping("/reissue-token")
    fun reissueToken(
        @RequestHeader(value = "Authorization")
        refreshToken: String,
    ): ReissueTokenResponse {
        val tokenDto = authService.reissueToken(refreshToken)

        return ReissueTokenResponse(
            accessToken = tokenDto.accessToken,
            refreshToken = tokenDto.refreshToken
        )
    }
}