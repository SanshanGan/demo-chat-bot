package com.example.demochatbot.common.exception

import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice


@RestControllerAdvice
class GlobalExceptionHandler {
	@ExceptionHandler(value = [Exception::class])
	fun handleException(e: Exception) = ErrorResponse("500", "Exception Occurred: ${e.message}")

	@ExceptionHandler(value = [BusinessException::class])
	fun handleServiceException(e: BusinessException) = ErrorResponse(e.code, e.message)

}