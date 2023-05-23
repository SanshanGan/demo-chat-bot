package com.example.demochatbot.conversation.chat.openai.api.dto

import com.fasterxml.jackson.annotation.JsonProperty

class ChatCompletionDTO {
    data class Response(
	    val model: String,
	    val `object`: String,
	    val usage: ChatUsage,
	    val id: String,
	    val created: Long,
	    val choices: List<ChatCompletionChoice>
    )
    data class Request(
        val model: String,
        val messages: List<ChatMessage>
    )

	data class ChatUsage(
		@JsonProperty(value = "prompt_tokens") val promptTokens: Int,
		@JsonProperty(value = "completion_tokens") val completionTokens: Int,
		@JsonProperty(value = "total_tokens") val totalTokens: Int
	)

	data class ChatCompletionChoice(
		val index: Int,
		val delta: String? = null,
		val message: ChatMessage,
		@JsonProperty(value = "finish_reason") val finishReason: String,
	)

	data class ChatMessage(
		val role: String,
		val content: String
	)
}