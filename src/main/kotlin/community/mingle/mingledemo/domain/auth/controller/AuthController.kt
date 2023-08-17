package community.mingle.mingledemo.domain.auth.controller

import community.mingle.mingledemo.domain.auth.controller.request.EmailRequest
import community.mingle.mingledemo.domain.auth.util.sha256
import community.mingle.mingledemo.domain.member.service.MemberService
import community.mingle.mingledemo.exception.DuplicatedEmailException
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController(
    private val memberService: MemberService,
) {

    @Operation(
        summary = "회원가입 화면에서 이메일 형식 및 중복 검증",
        responses = [
            ApiResponse(responseCode = "200", description = "OK"),
            ApiResponse(responseCode = "400", description = "DUPLICATED_EMAIL"),
            ApiResponse(responseCode = "400", description = "DUPLICATED_EMAIL"),
        ],
    )
    @PostMapping("/check-email")
    fun verifyEmail(
        @RequestBody
        @Valid
        emailRequest: EmailRequest
    ): Boolean {
        val email = emailRequest.email

        if (memberService.isMemberExistedByEmail(email.sha256())) {
            throw DuplicatedEmailException()
        } else return true
    }
}