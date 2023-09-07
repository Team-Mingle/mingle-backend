package community.mingle.mingledemo.exception

import community.mingle.mingledemo.exception.base.ResponseStatusReasonException
import org.springframework.http.HttpStatus

class InvalidPostAccess : ResponseStatusReasonException(
    statusCode = HttpStatus.UNAUTHORIZED,
    reasonName = "INVALID_POST_READ_ACCESS",
    reasonMessage = "접근 권한이 없는 게시글 입니다.",
)