package com.alok.readmessageapp.viewmodelFactory

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alok.readmessageapp.room.MessageDB
import com.alok.readmessageapp.room.MessageInfo
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Alok Soni on 17/01/21.
 */
class MessageViewModel(application:Application): AndroidViewModel(application) {

    private val msgList = MutableLiveData<List<MessageInfo>>()
    val messages: LiveData<List<MessageInfo>> = msgList

    private val context = getApplication<Application>().applicationContext

    fun loadMessageList() {
        GlobalScope.launch {
            msgList.postValue(MessageDB.getDatabase(context)?.messageDao()?.getAllMessages())
        }
    }

    fun insertMessage(messageInfo: MessageInfo){
        GlobalScope.launch {
            Log.d("MessageViewModel", "insertMessage: called...")
            MessageDB.getDatabase(context)?.messageDao()?.insert(messageInfo)
            msgList.postValue(MessageDB.getDatabase(context)?.messageDao()?.getAllMessages())
            Log.d("MessageViewModel", "$$$$$: ${MessageDB.getDatabase(context)?.messageDao()?.getAllMessages()}")
        }
    }

}