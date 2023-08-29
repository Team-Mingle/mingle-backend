package community.mingle.mingledemo.exception

import community.mingle.mingledemo.exception.base.ResponseStatusReasonException
import org.springframework.http.HttpStatus

class ReportedMemberSignUpException : ResponseStatusReasonException(
    statusCode = HttpStatus.FORBIDDEN,
    reasonName = "REPORTED_MEMBER_NOT_ALLOWED",
    reasonMessage = "신고된 유저는 재가입 할 수 없습니다.",
)