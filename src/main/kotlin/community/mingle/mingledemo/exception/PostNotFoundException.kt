package community.mingle.mingledemo.exception

import community.mingle.mingledemo.exception.base.ResponseStatusReasonException
import org.springframework.http.HttpStatus

class PostNotFoundException : ResponseStatusReasonException(
statusCode = HttpStatus.NOT_FOUND,
reasonName = "POST_NOT_FOUND",
reasonMessage = "게시물을 찾을 수 없습니다.",
)