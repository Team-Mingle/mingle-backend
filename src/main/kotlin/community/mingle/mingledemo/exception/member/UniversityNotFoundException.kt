package community.mingle.mingledemo.exception.member

import community.mingle.mingledemo.exception.base.ResponseStatusReasonException
import org.springframework.http.HttpStatus

class UniversityNotFoundException : ResponseStatusReasonException(
    statusCode = HttpStatus.NOT_FOUND,
    code = 3004,
    reasonMessage = "대학교 정보를 찾을 수 없습니다.",
)