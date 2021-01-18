package com.alok.readmessageapp.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import java.lang.Long


/**
 * Created by Alok Soni on 16/01/21.
 */

class UtilityMethods{
    companion object{
        /**
         * This method save  a String in Application prefs
         *
         * @param context
         * @param key
         * @param value
         */
        fun saveBooleanInPref(context: Context, key: String?, value: Boolean) {
            try {
                val prefs: SharedPreferences = context.getSharedPreferences(key, Context.MODE_PRIVATE)
                val editor = prefs.edit()
                editor.putBoolean(key, value)
                editor.commit()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        /**
         * This method returns the string value stored in Application prefs against a key
         *
         * @param context
         * @param key
         * @param defaultValue
         * @return
         */
        fun getBooleanInPref(context: Context?, key: String?, defaultValue: Boolean): Boolean? {
            try {
                val prefs: SharedPreferences = context!!.getSharedPreferences(key, Context.MODE_PRIVATE)
                return prefs.getBoolean(key, defaultValue)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return false
        }

        fun isPromotionalMsg (str: String): Boolean{
            var isPromotioanl = false
            try {
                var newStr = str.trim().replace("+","").replace("-","")
                Long.parseLong(newStr)
            } catch (e: NumberFormatException) {
                Log.d("UtilityMethods", "exception: ${e.printStackTrace()}")
                isPromotioanl = true
            }
            return isPromotioanl
        }
    }
}