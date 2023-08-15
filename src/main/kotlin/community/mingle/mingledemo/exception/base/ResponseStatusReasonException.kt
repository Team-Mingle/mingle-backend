package community.mingle.mingledemo.exception.base

import community.mingle.mingledemo.dto.ExceptionReasonDto
import org.springframework.http.HttpStatusCode
import org.springframework.web.server.ResponseStatusException

abstract class ResponseStatusReasonException(
    statusCode: HttpStatusCode,
    val reasonName: String,
    val reasonMessage: String,
): ResponseStatusException(statusCode, reasonMessage) {
    fun toExceptionReason(): ExceptionReasonDto {
        return ExceptionReasonDto(
            status = statusCode,
            reason = reasonName,
            message = reasonMessage,
        )
    }
}

