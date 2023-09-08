package community.mingle.mingledemo.exception

import community.mingle.mingledemo.exception.base.ResponseStatusReasonException
import org.springframework.http.HttpStatus

class DuplicatedNicknameException: ResponseStatusReasonException(
    statusCode = HttpStatus.BAD_REQUEST,
    code = 1004,
    reasonMessage = "이미 사용 중인 닉네임입니다.",
)