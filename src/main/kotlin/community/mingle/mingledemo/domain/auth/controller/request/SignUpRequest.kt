package community.mingle.mingledemo.domain.auth.controller.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import java.io.Serializable

data class SignUpRequest (

    val universityId: Int,

    @field:Email(message = "INVALID EMAIL FORMAT")
    @field:NotBlank(message = "EMAIL SHOULD NOT BE EMPTY")
    val email: String,

//    @field:Min(value = 6, message = "PASSWORD SHOULD BE GREATER THAN 6")
    @field:NotBlank(message = "PASSWORD SHOULD NOT BE EMPTY")
    val password: String,

    @field:NotEmpty
    @field:NotBlank(message = "NICKNAME SHOULD NOT BE EMPTY")
    val nickname: String,

    @field:NotEmpty
    @field:NotBlank(message = "FCM TOKEN SHOULD NOT BE EMPTY")
    val fcmToken: String,
): Serializable