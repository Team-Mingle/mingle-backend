package community.mingle.mingledemo.exception.auth

import community.mingle.mingledemo.exception.base.ResponseStatusReasonException
import org.springframework.http.HttpStatus

class ReportedMemberLoginException : ResponseStatusReasonException(
    statusCode = HttpStatus.FORBIDDEN,
    code = 1007,
    reasonMessage = "신고된 유저는 로그인 할 수 없습니다.",
)