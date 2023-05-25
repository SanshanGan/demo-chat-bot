package com.example.demochatbot.conversation.service.model

import com.example.demochatbot.conversation.repository.doc.Status


data class Message(
	val id: String,
	var markStatus: Status,
	val question: String,
	val answer: String,
	val createdAt: Long
)
