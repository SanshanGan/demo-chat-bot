package com.example.demochatbot.conversation.repository.doc

import org.springframework.data.annotation.Id
import java.time.Instant
import java.util.*

data class ConversationDoc(
	@Id
	val id: String = UUID.randomUUID().toString(),
	var markStatus: Boolean = false,
	val question: ConversationTemplate,
	val answer: ConversationTemplate,
	val createdAt: Long = Instant.now().toEpochMilli()
)

data class ConversationTemplate(
	val content: String,
	val user: String,
)