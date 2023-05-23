package com.example.demochatbot.conversation.service.mapper

import com.example.demochatbot.conversation.repository.doc.ConversationDoc
import com.example.demochatbot.conversation.service.model.Conversation

fun toConversation(conversationDoc: ConversationDoc) = Conversation(
	id = conversationDoc.id,
	markStatus = conversationDoc.markStatus,
	question = conversationDoc.question.content,
	answer = conversationDoc.answer.content,
	createdAt = conversationDoc.createdAt,
)