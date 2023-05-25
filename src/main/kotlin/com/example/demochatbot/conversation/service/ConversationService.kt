package com.example.demochatbot.conversation.service

import com.example.demochatbot.common.exception.BusinessException
import com.example.demochatbot.conversation.chat.CoreChat
import com.example.demochatbot.conversation.repository.ConversationRepository
import com.example.demochatbot.conversation.controller.model.ConversationDTO
import com.example.demochatbot.conversation.repository.doc.ConversationTemplate
import com.example.demochatbot.conversation.repository.doc.ConversationDoc
import com.example.demochatbot.conversation.service.mapper.toMessage
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ConversationService(
	private val conversationRepo: ConversationRepository,
	private val coreChat: CoreChat
) {
	fun getOpenAIResponse(chatbotRequest: ConversationDTO.Request): ConversationDTO.Response {
		val answer = coreChat.conversation(chatbotRequest)
		val savedConversation = conversationRepo.save(
			ConversationDoc(
				question = ConversationTemplate(
					content = chatbotRequest.prompt,
					user = "user"
				),
				answer = ConversationTemplate(
					content = answer,
					user = "chatbot"
				)
			)
		)
		return ConversationDTO.Response(id = savedConversation.id, response = answer)
	}

	fun getConversationHistory() = conversationRepo.findAll().map { toMessage(it) }

	fun getMessagesByStatus(markStatus: Boolean) =
		conversationRepo.findByMarkStatus(markStatus).map { toMessage(it) }

	fun changeMessageStatus(id: String, markStatus: Boolean) {
		val message = conversationRepo.findByIdOrNull(id) ?: throw BusinessException.ConversationNotFoundException()
		message.markStatus = markStatus
		conversationRepo.save(message)
	}
}