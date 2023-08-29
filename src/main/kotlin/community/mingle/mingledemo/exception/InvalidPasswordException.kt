package community.mingle.mingledemo.exception

import community.mingle.mingledemo.exception.base.ResponseStatusReasonException
import org.springframework.http.HttpStatus

class InvalidPasswordException: ResponseStatusReasonException(
    statusCode = HttpStatus.FORBIDDEN,
    reasonName = "INVALID_PASSWORD",
    reasonMessage = "올바르지 않은 비밀번호입니다.",
)