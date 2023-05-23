package com.example.demochatbot.conversation.service

import com.example.demochatbot.conversation.chat.openai.api.OpenAIApiClient
import com.example.demochatbot.conversation.repository.ConversationRepository
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.Test

class MessageServiceTest {
	@InjectMockKs
	private lateinit var conversationService: ConversationService
	@MockK
	private lateinit var openAIRepo: ConversationRepository
	@MockK
	private lateinit var openAIApiClient: OpenAIApiClient
	@Test
	fun `should return chatbot response successfully`() {

	}

	@Test
	fun getChatHistory() {
	}
}