package com.example.demochatbot.conversation.repository

import com.example.demochatbot.conversation.repository.doc.ConversationDoc
import com.example.demochatbot.conversation.repository.doc.Status
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ConversationRepository : MongoRepository<ConversationDoc, String> {
	fun findByMarkStatus(markStatus: Status): List<ConversationDoc>
}