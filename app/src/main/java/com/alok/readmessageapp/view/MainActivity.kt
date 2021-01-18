package com.alok.readmessageapp.view

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alok.androidexcersise.view.adapter.MessageListAdapter
import com.alok.readmessageapp.ICheckPermission
import com.alok.readmessageapp.R
import com.alok.readmessageapp.room.MessageInfo
import com.alok.readmessageapp.utils.Constant
import com.alok.readmessageapp.utils.UtilityMethods
import com.alok.readmessageapp.utils.snackbar
import com.alok.readmessageapp.viewmodelFactory.MessageViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ICheckPermission {
    companion object{
        private val TAG = MainActivity::class.simpleName;
    }
    private val READ_SMS_REQ_CODE = 100
    private var chkPerInterface :ICheckPermission? = null
    private var viewModel: MessageViewModel? = null
    private var messageListAdapter: MessageListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        initView()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_refresh -> {
                if(viewModel != null){
                    viewModel?.loadMessageList()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initView(){
        chkPerInterface = this
        if(hasPermissions()){
            chkPerInterface!!.onPermissionGranted()
        }else{
            requestPermission()
        }
    }

    private fun hasPermissions(): Boolean{
        val readSMSPer = ContextCompat.checkSelfPermission(this@MainActivity, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED
        val receiveSMSPer = ContextCompat.checkSelfPermission(this@MainActivity, Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED
        return readSMSPer && receiveSMSPer
    }


    private fun requestPermission(){
        ActivityCompat.requestPermissions(this@MainActivity,
                arrayOf(Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS), READ_SMS_REQ_CODE)
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            READ_SMS_REQ_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "onRequestPermissionsResult: granted")
                    chkPerInterface!!.onPermissionGranted()
                } else {
                    chkPerInterface!!.onPermissionDenied()
                }
                return
            }
        }
    }

//    private fun readAllTextMessage(): ArrayList<MessageInfo>{
//        var smsList = arrayListOf<MessageInfo>()
//        val cursor: Cursor? = contentResolver.query(
//            Telephony.Sms.Inbox.CONTENT_URI,
//                        arrayOf(Telephony.Sms.Inbox.BODY,Telephony.Sms.Inbox.ADDRESS,
//                            Telephony.Sms.Inbox.DATE,Telephony.Sms.Inbox.CREATOR,Telephony.Sms.Inbox.PERSON), null,
//                    null,Telephony.Sms.Inbox.DEFAULT_SORT_ORDER)
//        cursor?.let {
//            Log.d(TAG, "curesor : ${cursor}")
//            val count:Int? = cursor?.count-1
//            Log.d(TAG, "count: ${count}")
//            if(cursor != null && cursor!!.moveToNext()){
//                for (index in 0..count!!) {
//                    val sms = getSMS(cursor)
//                    smsList.add(sms)
//                    cursor.moveToNext()
//                }
//            }else{
//                Log.d(TAG, "readAllTextMessage: No sms in device")
//            }
//            cursor.close()
//        }
//        Log.d(TAG, "sms list size : ${smsList.size}")
//        for( i in 0..(smsList.size-8140)){
//            viewModel?.insertMessage(smsList.get(i))
//            Log.d(TAG, "i= ${i} -> ${smsList.get(i)}")
//        }
//
//        //testing
//        val body = "hi this is text msgf"
//        val sender = "JD-Jio"
//        val date = 1610969314000
//        val sms = MessageInfo(0,sender,body,date)
//        viewModel?.insertMessage(sms)
//        smsList.add(sms)
//        return smsList;
//    }
//
//    private fun getSMS(cursor: Cursor):MessageInfo{
//        val body = cursor.getString(0)
//        val sender = cursor.getString(1)
//        val date = cursor.getLong(2)
//        val sms = MessageInfo(sender,body,date)
//        return sms
//    }

    private fun setupViewModel(){
        viewModel = ViewModelProvider(this).get(MessageViewModel::class.java)
        viewModel?.loadMessageList()
    }

    private fun setListAdapter(){
        messageListAdapter = MessageListAdapter(viewModel?.messages?.value?: emptyList())
        messageListRecyclerView.layoutManager = LinearLayoutManager(this)
        messageListRecyclerView.adapter = messageListAdapter
    }

    override fun onPermissionGranted() {
        Log.d(TAG, "onPermissionGranted: called...")
        UtilityMethods.saveBooleanInPref(this,Constant.PREFERENCE_KEY_PERMISSION,true)
        setupViewModel()
        setListAdapter()
        Log.d(TAG, "viewModel: ${viewModel}")
        Log.d(TAG, "viewModel.messages: ${viewModel?.messages}")
        viewModel?.messages?.observe(this, Observer {
            println("###### ${it}")
            if(it.size == 0){
                messageListRecyclerView.visibility = View.GONE
                emptyLayout.visibility = View.VISIBLE
            }else{
                messageListRecyclerView.visibility = View.VISIBLE
                emptyLayout.visibility = View.GONE
            }
            messageListAdapter?.update(it)
        })

        //readAllTextMessage()
    }

    override fun onPermissionDenied() {
        findViewById<View>(R.id.rootView).snackbar("Permisson Denied", Snackbar.LENGTH_LONG)
    }
}