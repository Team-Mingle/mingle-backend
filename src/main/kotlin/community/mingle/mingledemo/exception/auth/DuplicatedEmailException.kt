package community.mingle.mingledemo.exception.auth

import community.mingle.mingledemo.exception.base.ResponseStatusReasonException
import org.springframework.http.HttpStatus

class DuplicatedEmailException : ResponseStatusReasonException(
    statusCode = HttpStatus.BAD_REQUEST,
    code = 1003,
    reasonMessage = "이미 가입된 이메일입니다.",
)