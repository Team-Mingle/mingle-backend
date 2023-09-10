package community.mingle.mingledemo.exception.auth

import community.mingle.mingledemo.exception.base.ResponseStatusReasonException
import org.springframework.http.HttpStatus

class AuthenticateFailedException : ResponseStatusReasonException(
    statusCode = HttpStatus.UNAUTHORIZED,
    code = 1001,
    reasonMessage = "잘못된 인증 정보입니다.",
)
