package community.mingle.mingledemo.exception

import community.mingle.mingledemo.exception.base.ResponseStatusReasonException
import org.springframework.http.HttpStatus

class MemberNotFoundException : ResponseStatusReasonException(
    statusCode = HttpStatus.NOT_FOUND,
    reasonName = "MEMBER_NOT_FOUND",
    reasonMessage = "회원을 찾을 수 없습니다.",
)