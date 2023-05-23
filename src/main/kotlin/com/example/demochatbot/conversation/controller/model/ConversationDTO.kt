package com.example.demochatbot.conversation.controller.model

class ConversationDTO {
	data class Request(
		val role: String,
		val prompt: String
	)
	data class Response(
		val id: String,
		val response: String
	)
}