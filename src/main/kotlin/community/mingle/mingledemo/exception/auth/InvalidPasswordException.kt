package community.mingle.mingledemo.exception.auth

import community.mingle.mingledemo.exception.base.ResponseStatusReasonException
import org.springframework.http.HttpStatus

class InvalidPasswordException : ResponseStatusReasonException(
    statusCode = HttpStatus.FORBIDDEN,
    code = 1005,
    reasonMessage = "올바르지 않은 비밀번호입니다.",
)