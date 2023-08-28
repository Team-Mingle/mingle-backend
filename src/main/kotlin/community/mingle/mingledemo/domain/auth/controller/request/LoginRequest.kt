package community.mingle.mingledemo.domain.auth.controller.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import java.io.Serializable

data class LoginRequest (
    @field:Email(message = "INVALID EMAIL FORMAT")
    @field:NotBlank(message = "EMAIL SHOULD NOT BE EMPTY")
    val email: String,

    @field:NotBlank(message = "PASSWORD SHOULD NOT BE EMPTY")
    val password: String,

    @field:NotBlank(message = "FCM-TOKEN SHOULD NOT BE EMPTY")
    val fcmToken: String,
): Serializable