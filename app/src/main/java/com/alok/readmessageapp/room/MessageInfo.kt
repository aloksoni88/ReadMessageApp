package com.alok.readmessageapp.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Alok Soni on 16/01/21.
 */
@Entity(tableName = "messageinfo")
data class MessageInfo(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name="Id") val id: Long,
        @ColumnInfo(name = "Sender") val sender: String?,
        @ColumnInfo(name = "MsgBody") val msgBody: String?,
        @ColumnInfo(name = "Date") val date: Long?
){
        constructor(sender: String?, msgBody: String?, date: Long?): this(0,sender,msgBody,date)
}