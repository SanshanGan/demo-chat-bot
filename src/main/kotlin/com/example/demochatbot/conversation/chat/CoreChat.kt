package com.example.demochatbot.conversation.chat

import com.example.demochatbot.conversation.controller.model.ConversationDTO

interface CoreChat {
	fun conversation(chatbotRequest: ConversationDTO.Request): String
}