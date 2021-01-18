package com.alok.readmessageapp.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

/**
 * Created by Alok Soni on 16/01/21.
 */

/**
 * Created by Alok Soni on 13/11/20.
 */
fun View.snackbar(message: String, duration: Int = Snackbar.LENGTH_LONG) {
    Snackbar.make(this, message, duration).show()
}