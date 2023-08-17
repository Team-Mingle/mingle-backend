package community.mingle.mingledemo.domain.auth.controller.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

data class EmailRequest(
    @field:Email(message = "INVALID EMAIL FORMAT")
    @field:NotEmpty(message = "EMAIL SHOULD NOT BE EMPTY")
    val email: String
)
