package com.example.demochatbot.conversation.repository

import com.example.demochatbot.conversation.repository.doc.ConversationDoc
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ConversationRepository : MongoRepository<ConversationDoc, String> {
	fun findByMarkStatus(markStatus: Boolean): List<ConversationDoc>
}