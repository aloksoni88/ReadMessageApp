package com.alok.readmessageapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Created by Alok Soni on 16/01/21.
 */

@Database(entities = [MessageInfo::class], version = 1)
abstract class MessageDB : RoomDatabase() {
    abstract fun messageDao(): MessageDao

    companion object {
        private var INSTANCE: MessageDB? = null

        fun getDatabase(context: Context): MessageDB? {
            if (INSTANCE == null) {
                synchronized(MessageDB::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        MessageDB::class.java, "message.db"
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}