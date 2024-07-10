package com.example.mediatestapp.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class MediaScannerReceiver : BroadcastReceiver() {

    companion object {
        private val LOG_TAG = "TestMediaAppReceive"
    }

    interface IScanMediaCallBack {
        fun onPreScan()
        fun onMetaScan()
    }

    var scanMediaCallBack: IScanMediaCallBack? = null

    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.let {
            Log.i(LOG_TAG, "onReceive action : ${it.action}")
            if (MediaScannerActions.actions.contains(it.action)) {
                when(it.action) {
                    MediaScannerActions.ACTION_MEDIA_SCANNER_PRESCAN_FINISHED -> {
                        scanMediaCallBack?.onPreScan()
                    }
                    Intent.ACTION_MEDIA_SCANNER_FINISHED -> {
                        scanMediaCallBack?.onMetaScan()
                    }
                    else -> {
                        //do nothing
                    }
                }
                Toast.makeText(context, "Action : ${it.action}", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "UnSupportedAction", Toast.LENGTH_SHORT).show()
            }
        }
    }

}