package com.example.demochatbot.conversation.chat.openai.api.config

import com.example.demochatbot.common.exception.BusinessException
import feign.Response
import feign.codec.ErrorDecoder

class OpenAIErrorDecoderConfiguration : ErrorDecoder {
	override fun decode(methodKey: String, response: Response): Exception =
		when (response.status()) {
			401 -> BusinessException.OpenAIException.Unauthorized
			429 -> BusinessException.OpenAIException.ExceededCurrentQuota
			else -> BusinessException.OpenAIException.ServerError
		}
}