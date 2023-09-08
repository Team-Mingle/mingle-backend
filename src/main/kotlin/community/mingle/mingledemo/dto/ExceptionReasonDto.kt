package community.mingle.mingledemo.dto

import org.springframework.http.HttpStatusCode

data class ExceptionReasonDto(
        val status: HttpStatusCode,
        val code: Int,
        val message: String,
)
