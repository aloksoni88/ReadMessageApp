package com.alok.androidexcersise.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alok.readmessageapp.databinding.MessageListItemBinding
import com.alok.readmessageapp.room.MessageInfo
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by Alok Soni on 16/01/21.
 */

class MessageListAdapter(private var messages: List<MessageInfo>) :
    RecyclerView.Adapter<MessageListAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MessageListItemBinding.inflate(inflater,parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: MyViewHolder, position: Int) {
        viewHolder.bind(messages[position])
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    fun update(data: List<MessageInfo>) {
        messages = data
        notifyDataSetChanged()
    }


    class MyViewHolder(val binding: MessageListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(messageInfo: MessageInfo) {
            binding.messageInfoItem = messageInfo
        }
    }

    companion object {
        val TAG = MessageListAdapter::class.java.simpleName

        @JvmStatic
        fun getDateFormat(date: Long): String{
            val date = Date(date)
            val dateInFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss a")
            return dateInFormat.format(date)
        }
    }
}