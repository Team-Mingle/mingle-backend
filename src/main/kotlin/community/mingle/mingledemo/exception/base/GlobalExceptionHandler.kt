package community.mingle.mingledemo.exception.base

import community.mingle.mingledemo.dto.ExceptionReasonDto
import community.mingle.mingledemo.exception.auth.CustomMethodArgumentNotValidException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(e: MethodArgumentNotValidException): ResponseEntity<ExceptionReasonDto> {
        val errors = e.bindingResult.allErrors
        val errorMessages = errors.joinToString(", ") { it.defaultMessage ?: "Unknown error" }
        return ResponseEntity
            .status(e.statusCode)
            .body(CustomMethodArgumentNotValidException(errorMessages).toExceptionReason())

    }
}