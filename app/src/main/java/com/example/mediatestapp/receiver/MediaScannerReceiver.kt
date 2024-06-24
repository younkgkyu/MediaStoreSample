package com.example.mediatestapp.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class MediaScannerReceiver : BroadcastReceiver() {

    companion object {
        private val LOG_TAG = MediaScannerReceiver::class.java.simpleName
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.let {
            Log.i(LOG_TAG, "onReceive action : ${it.action}")
            if (MediaScannerActions.actions.contains(it.action)) {
                Toast.makeText(context, "Action : ${it.action}", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "UnSupportedAction", Toast.LENGTH_SHORT).show()
            }
        }
    }

}