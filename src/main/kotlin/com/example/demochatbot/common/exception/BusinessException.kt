package com.example.demochatbot.common.exception


sealed class BusinessException(val code: String, override val message: String) : RuntimeException(message) {

	class ConversationNotFoundException(code: String = "100000", message: String = "The conversation dose not exist") :
		BusinessException(code, message)

	sealed class OpenAIException(code: String, message: String) : BusinessException(code, message) {
		object ServerError : OpenAIException("200000", "OpenAI server error, please try again later.")

		object Unauthorized : OpenAIException("200001", "There is an error in your OpenAI key, please re-try.")

		object ExceededCurrentQuota : OpenAIException("200002", "Your OpenAI key has exceeded the number of uses for the day, please try again tomorrow.")
	}
}


