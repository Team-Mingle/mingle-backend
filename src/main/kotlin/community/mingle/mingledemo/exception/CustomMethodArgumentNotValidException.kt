package community.mingle.mingledemo.exception

import community.mingle.mingledemo.exception.base.ResponseStatusReasonException
import org.springframework.http.HttpStatus

class CustomMethodArgumentNotValidException(reasonName: String): ResponseStatusReasonException(
    statusCode = HttpStatus.UNAUTHORIZED,
    code = 1002,
    reasonMessage = reasonName,
)