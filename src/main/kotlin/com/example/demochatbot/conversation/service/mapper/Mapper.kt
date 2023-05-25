package com.example.demochatbot.conversation.service.mapper

import com.example.demochatbot.conversation.repository.doc.ConversationDoc
import com.example.demochatbot.conversation.service.model.Message

fun toMessage(conversationDoc: ConversationDoc) = Message(
	id = conversationDoc.id,
	markStatus = conversationDoc.markStatus,
	question = conversationDoc.question.content,
	answer = conversationDoc.answer.content,
	createdAt = conversationDoc.createdAt,
)