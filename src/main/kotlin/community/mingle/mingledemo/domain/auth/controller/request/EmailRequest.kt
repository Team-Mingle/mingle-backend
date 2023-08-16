package community.mingle.mingledemo.domain.auth.controller.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

data class EmailRequest(

    @NotEmpty(message = "EMAIL SHOULD NOT BE EMPTY")
    @Email(message = "INVALID EMAIL FORMAT")
    @NotNull(message = "not null")
    val email: String
)
