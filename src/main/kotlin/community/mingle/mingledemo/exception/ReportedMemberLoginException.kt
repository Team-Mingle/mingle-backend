package community.mingle.mingledemo.exception

import community.mingle.mingledemo.exception.base.ResponseStatusReasonException
import org.springframework.http.HttpStatus

class ReportedMemberLoginException : ResponseStatusReasonException(
    statusCode = HttpStatus.FORBIDDEN,
    reasonName = "REPORTED_MEMBER_NOT_ALLOWED",
    reasonMessage = "신고된 유저는 로그인 할 수 없습니다.",
)