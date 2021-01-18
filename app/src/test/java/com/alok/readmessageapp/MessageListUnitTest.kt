package com.alok.readmessageapp

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.alok.readmessageapp.room.MessageInfo
import com.alok.readmessageapp.viewmodelFactory.MessageViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

/**
 * Created by Alok Soni on 18/01/21.
 */

class MessageListUnitTest{

    @Mock
    private lateinit var context: Application

    private lateinit var viewModel: MessageViewModel

    private lateinit var messageList:MutableList<List<MessageInfo>>

    private lateinit var onSuccessMsgListObserver: Observer<List<MessageInfo>>

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        Mockito.`when`(context.applicationContext).thenReturn(context)

        viewModel = MessageViewModel(context)
        setupObservers()
        createMessageList()
    }

    @Test
    fun `get messages with ViewModel returns all messages`() {
        with(viewModel) {
            loadMessageList()
        }

        Assert.assertTrue(viewModel.messages.value?.size == 3)
    }

    @Test
    fun `get messages with ViewModel returns an empty`() {
        with(viewModel) {
            loadMessageList()
        }

        Assert.assertNotNull(viewModel.messages.value?.size == 0)
    }

    private fun setupObservers() {
        onSuccessMsgListObserver = Mockito.mock(Observer::class.java) as Observer<List<MessageInfo>>
    }

    private fun createMessageList() {

        val list: MutableList<List<MessageInfo>> = mutableListOf()
        var mlist: MutableList<MessageInfo> = arrayListOf()

        mlist.add(
            MessageInfo(
                "Sender1",
                "This is msg 1 body",
                1610969314000
            )
        )
        mlist.add(MessageInfo("Sender2", "This is msg 2 body", 1610969315000))
        mlist.add(MessageInfo("Sender3", "This is msg 3 body", 1610969316000))
        list.add(mlist)
        messageList = list
    }
}