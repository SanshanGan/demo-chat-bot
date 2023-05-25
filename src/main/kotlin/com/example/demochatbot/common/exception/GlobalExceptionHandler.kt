package com.example.demochatbot.common.exception

import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageConversionException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice


@RestControllerAdvice
class GlobalExceptionHandler {
	@ExceptionHandler(value = [Exception::class])
	fun handleException(e: Exception) = ErrorResponse("500", "Exception Occurred: ${e.message}")

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = [BusinessException::class, HttpMessageConversionException::class])
	fun handleServiceException(e: BusinessException) = ErrorResponse(e.code, e.message)

}