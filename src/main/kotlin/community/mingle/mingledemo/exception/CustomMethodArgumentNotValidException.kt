package community.mingle.mingledemo.exception

import community.mingle.mingledemo.exception.base.ResponseStatusReasonException
import org.springframework.http.HttpStatus

class CustomMethodArgumentNotValidException(reasonName: String): ResponseStatusReasonException(
    statusCode = HttpStatus.UNAUTHORIZED,
    reasonName = reasonName,
    reasonMessage = "잘못된 정보입니다.",
)