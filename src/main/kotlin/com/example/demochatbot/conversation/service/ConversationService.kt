package com.example.demochatbot.conversation.service

import com.example.demochatbot.common.exception.BusinessException
import com.example.demochatbot.conversation.chat.CoreChat
import com.example.demochatbot.conversation.repository.ConversationRepository
import com.example.demochatbot.conversation.controller.model.ConversationDTO
import com.example.demochatbot.conversation.repository.doc.ConversationTemplate
import com.example.demochatbot.conversation.repository.doc.ConversationDoc
import com.example.demochatbot.conversation.repository.doc.Status
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
				status = Status(),
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

	fun getMessagesByStatus(markStatus: Status) =
		conversationRepo.findByMarkStatus(markStatus).map { toMessage(it) }

	fun changeMessageStatus(id: String, markStatus: Status) {
		val message = conversationRepo.findByIdOrNull(id) ?: throw BusinessException.ConversationNotFoundException()
		message.status = markStatus
		conversationRepo.save(message)
	}
}