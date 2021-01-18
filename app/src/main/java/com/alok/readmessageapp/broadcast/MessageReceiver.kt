package com.alok.readmessageapp.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.alok.readmessageapp.room.MessageDB
import com.alok.readmessageapp.room.MessageInfo
import com.alok.readmessageapp.utils.Constant
import com.alok.readmessageapp.utils.UtilityMethods
import com.alok.readmessageapp.viewmodelFactory.MessageViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


/**
 * Created by Alok Soni on 16/01/21.
 */

class MessageReceiver : BroadcastReceiver() {

    companion object{
        private val TAG = MessageReceiver::class.simpleName
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val hasPermisison:Boolean? = UtilityMethods.getBooleanInPref(context,Constant.PREFERENCE_KEY_PERMISSION,false)
        if(hasPermisison == null || !hasPermisison){
            Log.d(TAG, "onReceive: Permission not provided...")
            return
        }
        //val messageDB = MessageDB.getDatabase(context!!)

        Log.d(TAG, "onReceive: ")
        val data = intent!!.extras
        val pdus = data!!["pdus"] as Array<Any>?
        for (i in pdus!!.indices) {
            val smsMessage: SmsMessage = SmsMessage.createFromPdu(pdus!![i] as ByteArray)
            Log.d(TAG, "onReceive: smsMessage- ${smsMessage}")

            val sender = smsMessage.getDisplayOriginatingAddress()?.toString()
            val msgBody = smsMessage.getMessageBody()?.toString()
            val time = smsMessage.getTimestampMillis()

            if(sender != null && UtilityMethods.isPromotionalMsg(sender)){
                val msg =  MessageInfo(sender,msgBody,time)
                Log.d(TAG, "onReceive: ${msg}")
                if (context != null) {
                    insertDataIntoTable(context, msg)
                }
            }
        }
    }

    private fun insertDataIntoTable(context: Context, msgInfo: MessageInfo){
        GlobalScope.launch {
            MessageDB.getDatabase(context)?.messageDao()?.insert(msgInfo)
        }
    }

}