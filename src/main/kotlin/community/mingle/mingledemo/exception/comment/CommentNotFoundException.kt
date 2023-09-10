package community.mingle.mingledemo.exception.comment

import community.mingle.mingledemo.exception.base.ResponseStatusReasonException
import org.springframework.http.HttpStatus

class CommentNotFoundException : ResponseStatusReasonException(
    statusCode = HttpStatus.NOT_FOUND,
    code = 4001,
    reasonMessage = "댓글을 찾을 수 없습니다.",
)