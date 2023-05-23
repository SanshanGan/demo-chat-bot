package com.example.demochatbot.conversation.repository.doc

import org.springframework.data.annotation.Id
import java.time.Instant
import java.util.*

data class OpenAIV2Doc(
	@Id
	val id: String = UUID.randomUUID().toString(),
	val note: Note,
	val question: ConversationV2,
	val answer: ConversationV2,
	val createdAt: Long = Instant.now().toEpochMilli()
)

data class ConversationV2(
	val content: String,
	val user: String
)

data class Note(
	val markStatus: Boolean,
	val annotation: String? = "No annotation here"
)