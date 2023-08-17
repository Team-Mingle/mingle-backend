package community.mingle.mingledemo.exception

import community.mingle.mingledemo.exception.base.ResponseStatusReasonException
import org.springframework.http.HttpStatus

class UniversityNotFoundException : ResponseStatusReasonException(
    statusCode = HttpStatus.BAD_REQUEST,
    reasonName = "UNIVERSITY_NOT_FOUND",
    reasonMessage = "해당 대학교가 존재하지 않습니다.",
)