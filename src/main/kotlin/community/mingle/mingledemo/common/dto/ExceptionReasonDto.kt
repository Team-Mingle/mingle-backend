package community.mingle.mingledemo.common.dto

import org.springframework.http.HttpStatusCode

data class ExceptionReasonDto(
    val status: HttpStatusCode,
    val reason: String,
    val message: String,
)
