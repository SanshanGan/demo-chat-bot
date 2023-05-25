package com.example.demochatbot.conversation.controller

import com.example.demochatbot.conversation.chat.CoreChat
import com.example.demochatbot.conversation.controller.model.ConversationDTO
import com.example.demochatbot.conversation.repository.ConversationRepository
import com.example.demochatbot.conversation.repository.doc.ConversationDoc
import com.example.demochatbot.conversation.repository.doc.ConversationTemplate
import com.example.demochatbot.conversation.service.ConversationService
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions
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

	fun givenConversationDoc(markStatus: Boolean) = ConversationDoc(
		id = UUID.randomUUID().toString(),
		markStatus = markStatus,
		question = ConversationTemplate(content = "test prompt", user = "user"),
		answer = ConversationTemplate(content = "returned answer.", user = "chatbot")
	)

	@Nested
	@DisplayName("GetOpenAIResponse")
	@TestInstance(TestInstance.Lifecycle.PER_CLASS)
	inner class GetOpenAIResponse {
		@Test
		fun `should return OpenAI result`() {
			val chatbotRequest = ConversationDTO.Request(
				role = "user",
				prompt = "test prompt"
			)
			every { coreChat.conversation(any()) } returns "returned answer."
			every { conversationRepo.save(any()) } returns givenConversationDoc(false)            //when
			//when
			val openAIResponse = conversationService.getOpenAIResponse(chatbotRequest)
			//then
			Assertions.assertTrue(openAIResponse.id.isNotBlank())
			Assertions.assertEquals("returned answer.", openAIResponse.response)
			verify(exactly = 1) { coreChat.conversation(any()) }
			verify(exactly = 1) { conversationRepo.save(any()) }
		}
		@Test
		fun `should return error message if the OpenAI server error `() {
		    //given



		    //when



		    //then

		}
	}

	@Nested
	@DisplayName("GetConversationHistory")
	@TestInstance(TestInstance.Lifecycle.PER_CLASS)
	inner class GetConversationHistory {
		@Test
		fun `should get all the history of conversation`() {
			every { conversationRepo.findAll() } returns listOf(
				givenConversationDoc(false),
				givenConversationDoc(true)
			)
			//when
			val historyOfConversation = conversationService.getConversationHistory()
			//then
			Assertions.assertTrue(historyOfConversation[0].id.isNotBlank())
			Assertions.assertTrue(historyOfConversation[1].id.isNotBlank())
			Assertions.assertEquals("test prompt", historyOfConversation[0].question)
			Assertions.assertEquals("test prompt", historyOfConversation[1].question)
			Assertions.assertEquals(false, historyOfConversation[0].markStatus)
			Assertions.assertEquals(true, historyOfConversation[1].markStatus)
			verify(exactly = 1) { conversationRepo.findAll() }
		}
	}

	@Nested
	@DisplayName("GetMessagesByStatus")
	@TestInstance(TestInstance.Lifecycle.PER_CLASS)
	inner class GetMessagesByStatus {
		@Test
		fun `should give a list of messages with false status`() {
			every { conversationRepo.findByMarkStatus(false) } returns listOf(givenConversationDoc(false))
			//when
			val messagesByStatus = conversationService.getMessagesByStatus(false)
			//then
			val (_, markStatus, question, answer) = messagesByStatus.first()
			Assertions.assertTrue(messagesByStatus[0].id.isNotBlank())
			AssertionErrors.assertTrue("question must be equal", question == "test prompt")
			AssertionErrors.assertTrue("answer must be equal", answer == "returned answer.")
			AssertionErrors.assertTrue("marked must be false", !markStatus)
			verify(exactly = 1) { conversationRepo.findByMarkStatus(false) }
		}

		@Test
		fun `should give a list of messages with true status`() {
			every { conversationRepo.findByMarkStatus(true) } returns listOf(givenConversationDoc(true))
			//when
			val messagesByStatus = conversationService.getMessagesByStatus(true)
			val (_, markStatus, question, answer) = messagesByStatus.first()
			//then
			Assertions.assertTrue(messagesByStatus[0].id.isNotBlank())
			AssertionErrors.assertTrue("question must be equal", question == "test prompt")
			AssertionErrors.assertTrue("answer must be equal", answer == "returned answer.")
			AssertionErrors.assertTrue("marked must be true", markStatus)
			verify(exactly = 1) { conversationRepo.findByMarkStatus(true) }
		}
	}

	@Nested
	@DisplayName("ChangeMessageStatus")
	@TestInstance(TestInstance.Lifecycle.PER_CLASS)
	inner class ChangeMessageStatus {
		@Test
		fun `should change the message's status to true`() {
			val messageSet = givenConversationDoc(false)
			every { conversationRepo.findByIdOrNull("1") } returns messageSet
			every { conversationRepo.save(messageSet) } returns messageSet.copy(markStatus = true)
			//when
			conversationService.changeMessageStatus("1", true)
			//then
			AssertionErrors.assertTrue("marked must be changed to be true", messageSet.markStatus)
			verify(exactly = 1) { conversationRepo.save(messageSet) }
		}

		@Test
		fun `should change the message's status to false`() {
			val messageSet = givenConversationDoc(true)
			every { conversationRepo.findByIdOrNull("2") } returns messageSet
			every { conversationRepo.save(messageSet) } returns messageSet.copy(markStatus = false)
			//when
			conversationService.changeMessageStatus("2", false)
			//then
			AssertionErrors.assertTrue("marked must be changed to be false", !messageSet.markStatus)
			verify(exactly = 1) { conversationRepo.save(messageSet) }
		}

	}
}