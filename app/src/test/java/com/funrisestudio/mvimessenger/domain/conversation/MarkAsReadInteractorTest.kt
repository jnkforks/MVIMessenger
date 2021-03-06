package com.funrisestudio.mvimessenger.domain.conversation

import com.funrisestudio.mvimessenger.ui.conversation.ConversationAction
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class MarkAsReadInteractorTest {

    private val conversationRepository: ConversationRepository = mock()
    private lateinit var interactor: MarkAsReadInteractor

    @Before
    fun setUp() {
        interactor = MarkAsReadInteractor(conversationRepository)
    }

    @Test
    fun `should fetch conversations successfully`() = runBlockingTest {
        val contactId = 1
        whenever(conversationRepository.markMessagesAsRead(contactId)).thenReturn(Unit)

        val res = interactor.getFlow(ConversationAction.MarkAsRead(contactId)).toList()
        assertEquals(listOf(ConversationAction.MessagesMarkedAsRead), res)

        verify(conversationRepository).markMessagesAsRead(contactId)
        verifyNoMoreInteractions(conversationRepository)
    }

    @Test(expected = IllegalStateException::class)
    fun `should proceed with conversations exception`() = runBlockingTest {
        val contactId = 1
        doThrow(IllegalStateException()).whenever(conversationRepository).markMessagesAsRead(contactId)
        interactor.getFlow(ConversationAction.MarkAsRead(contactId)).toList()
    }

}