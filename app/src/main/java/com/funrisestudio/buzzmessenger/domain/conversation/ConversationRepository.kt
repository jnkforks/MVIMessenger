package com.funrisestudio.buzzmessenger.domain.conversation

import com.funrisestudio.buzzmessenger.domain.entity.Message
import kotlinx.coroutines.flow.Flow

interface ConversationRepository {

    fun getConversation(contactId: Int): Flow<List<Message>>

}