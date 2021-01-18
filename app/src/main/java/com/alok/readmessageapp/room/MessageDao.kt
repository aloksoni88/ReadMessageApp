package com.alok.readmessageapp.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


/**
 * Created by Alok Soni on 16/01/21.
 */
@Dao
interface MessageDao {
    @Insert
    fun insert(message: MessageInfo)


    @Query("SELECT * FROM messageinfo ORDER BY Date DESC")
    fun getAllMessages(): List<MessageInfo>
}