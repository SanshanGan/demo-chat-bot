package com.example.demochatbot.conversation.controller

import com.example.demochatbot.conversation.controller.model.ConversationDTO
import com.example.demochatbot.conversation.service.model.Message
import com.example.demochatbot.conversation.service.ConversationService
import com.example.demochatbot.conversation.service.model.MarkStatusDTO
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1")
class ConversationController(val conversationService: ConversationService) {
	@PostMapping("/conversation/openai")
	fun getOpenAIResponse(@RequestBody chatbotRequest: ConversationDTO.Request): ConversationDTO.Response =
		conversationService.getOpenAIResponse(chatbotRequest)

	@GetMapping("/conversation/history")
	fun getConversationHistory(): List<Message> = conversationService.getConversationHistory()

	@GetMapping("/conversation/messages")
	fun getMessagesByStatus(markStatus: Boolean) = conversationService.getMessagesByStatus(markStatus)

	@PatchMapping("/conversation/messages/{id}/change")
	fun changeMessageStatus(@PathVariable id: String, @RequestBody markStatus: MarkStatusDTO) =
		conversationService.changeMessageStatus(id, markStatus)

}