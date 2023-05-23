package com.example.demochatbot.conversation.chat.openai

import com.example.demochatbot.conversation.chat.CoreChat
import com.example.demochatbot.conversation.chat.openai.api.OpenAIApiClient
import com.example.demochatbot.conversation.chat.openai.api.dto.ChatCompletionDTO
import com.example.demochatbot.conversation.controller.model.ConversationDTO
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class OpenAIChat(
	val openAIApiClient: OpenAIApiClient
) : CoreChat {
	@Value("\${services.openai.model}")
	private lateinit var model: String

	@Value("\${services.openai.key}")
	private lateinit var key: String

	override fun conversation(chatbotRequest: ConversationDTO.Request): String {
		val gptRequest = ChatCompletionDTO.Request(
			model = model,
			messages = listOf(
				ChatCompletionDTO.ChatMessage(
					role = "user",
					content = chatbotRequest.prompt
				)
			)
		)
		return openAIApiClient.getChatCompletion("Bearer $key", gptRequest)
			.choices[0].message.content

	}
}