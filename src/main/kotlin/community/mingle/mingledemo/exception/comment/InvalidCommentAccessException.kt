package community.mingle.mingledemo.exception.comment

import community.mingle.mingledemo.exception.base.ResponseStatusReasonException
import org.springframework.http.HttpStatus

class InvalidCommentAccessException : ResponseStatusReasonException(
    statusCode = HttpStatus.UNAUTHORIZED,
    code = 3002,
    reasonMessage = "접근 권한이 없는 게시글 입니다.",
)