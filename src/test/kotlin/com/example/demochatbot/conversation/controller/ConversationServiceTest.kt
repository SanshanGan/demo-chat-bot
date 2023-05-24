package com.example.demochatbot.conversation.controller

import com.example.demochatbot.conversation.chat.CoreChat
import com.example.demochatbot.conversation.repository.ConversationRepository
import com.example.demochatbot.conversation.repository.doc.ConversationDoc
import com.example.demochatbot.conversation.repository.doc.ConversationTemplate
import com.example.demochatbot.conversation.service.ConversationService
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.util.AssertionErrors
import java.util.*

@ExtendWith(MockKExtension::class)
class ConversationServiceTest {
	@MockK
	private lateinit var conversationRepo: ConversationRepository

	@MockK
	private lateinit var coreChat: CoreChat

	@InjectMockKs
	private lateinit var conversationService: ConversationService

	@Nested
	@DisplayName("GET /v1/conversation/history")
	@TestInstance(TestInstance.Lifecycle.PER_CLASS)
	inner class GetConversationHistory {
		@Test
		fun `should get all the history of conversation`() {
		    //given



		    //when



		    //then

		}

	}

	@Nested
	@DisplayName("GET /v1/conversation/messages")
	@TestInstance(TestInstance.Lifecycle.PER_CLASS)
	inner class GetMessagesByStatus {
		@Test
		fun `should give a list of messages with false status`() {

			every { conversationRepo.findByMarkStatus(false) } returns listOf(
				ConversationDoc(
					id = "1",
					question = ConversationTemplate(content = "Hello", user = "user"),
					answer = ConversationTemplate(content = "bye", user = "chatbot"),
					markStatus = false
				)
			)
			//given
			val messagesByStatus = conversationService.getMessagesByStatus(false)
			//when/then
			val (id, markStatus, question, answer) = messagesByStatus.first()
			AssertionErrors.assertTrue("id must be equal", id == "1")
			AssertionErrors.assertTrue("question must be equal", question == "Hello")
			AssertionErrors.assertTrue("answer must be equal", answer == "bye")
			AssertionErrors.assertTrue("marked must be false", !markStatus)
			verify(exactly = 1) { conversationRepo.findByMarkStatus(false) }
		}

		@Test
		fun `should give a list of messages with true status`() {

			every { conversationRepo.findByMarkStatus(true) } returns listOf(
				ConversationDoc(
					id = "2",
					question = ConversationTemplate(content = "Hello", user = "user"),
					answer = ConversationTemplate(content = "bye", user = "chatbot"),
					markStatus = true
				)
			)
			//given
			val messagesByStatus = conversationService.getMessagesByStatus(true)
			val (id, markStatus, question, answer) = messagesByStatus.first()
			//when/then
			AssertionErrors.assertTrue("id must be equal", id == "2")
			AssertionErrors.assertTrue("question must be equal", question == "Hello")
			AssertionErrors.assertTrue("answer must be equal", answer == "bye")
			AssertionErrors.assertTrue("marked must be true", markStatus)
			verify(exactly = 1) { conversationRepo.findByMarkStatus(true) }
		}
	}
	@Nested
	@DisplayName("/conversation/messages/{id}/change")
	@TestInstance(TestInstance.Lifecycle.PER_CLASS)
	inner class ChangeMessageStatus {
		@Test
		fun `should change the message's status to true`() {
			val messageSet = ConversationDoc(
				id = "1",
				question = ConversationTemplate(content = "Hello", user = "user"),
				answer = ConversationTemplate(content = "bye", user = "chatbot"),
				markStatus = false
			)
			every { conversationRepo.findByIdOrNull("1") } returns messageSet
			every { conversationRepo.save(messageSet) } returns messageSet.copy(markStatus = true)
			//given
			conversationService.changeMessageStatus("1", true)
			//when/then
			AssertionErrors.assertTrue("marked must be changed to be true", messageSet.markStatus)
		}

		@Test
		fun `should change the message's status to false`() {

			val messageSet = ConversationDoc(
				id = "2",
				question = ConversationTemplate(content = "Hello", user = "user"),
				answer = ConversationTemplate(content = "bye", user = "chatbot"),
				markStatus = true
			)
			every { conversationRepo.findByIdOrNull("2") } returns messageSet
			every { conversationRepo.save(messageSet) } returns messageSet.copy(markStatus = false)
			//given
			conversationService.changeMessageStatus("2", false)
			//when/then
			AssertionErrors.assertTrue("marked must be changed to be false", !messageSet.markStatus)
		}

	}
}