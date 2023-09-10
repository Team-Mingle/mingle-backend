package community.mingle.mingledemo.exception.auth

import community.mingle.mingledemo.exception.base.ResponseStatusReasonException
import org.springframework.http.HttpStatus

class JwtTokenExpiredException : ResponseStatusReasonException(
    statusCode = HttpStatus.UNAUTHORIZED,
    code = 1006,
    reasonMessage = "토큰이 만료되었습니다",
)
