package com.alok.readmessageapp

/**
 * Created by Alok Soni on 16/01/21.
 */
interface ICheckPermission {
    fun onPermissionGranted()
    fun onPermissionDenied()
}