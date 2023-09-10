package community.mingle.mingledemo.exception.auth

import community.mingle.mingledemo.exception.base.ResponseStatusReasonException
import org.springframework.http.HttpStatus

class ReportedMemberSignUpException : ResponseStatusReasonException(
    statusCode = HttpStatus.FORBIDDEN,
    code = 1008,
    reasonMessage = "신고된 유저는 재가입 할 수 없습니다.",
)