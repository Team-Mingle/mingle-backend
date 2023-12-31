package community.mingle.mingledemo.exception.post

import community.mingle.mingledemo.exception.base.ResponseStatusReasonException
import org.springframework.http.HttpStatus

class InvalidPostAccessException : ResponseStatusReasonException(
    statusCode = HttpStatus.UNAUTHORIZED,
    code = 2001,
    reasonMessage = "접근 권한이 없는 게시글 입니다.",
)