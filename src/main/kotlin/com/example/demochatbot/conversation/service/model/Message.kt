package com.example.demochatbot.conversation.service.model


data class Message(
	val id: String,
	var markStatus: Boolean,
	val question: String,
	val answer: String,
	val createdAt: Long
)
