package com.example.mediatestapp.receiver

import android.content.Intent

object MediaScannerActions {

    const val ACTION_MEDIA_SCANNER_PRESCAN_FINISHED = "android.intent.action.MEDIA_SCANNER_PRESCAN_FINISHED"

    val actions = arrayOf(
        Intent.ACTION_MEDIA_UNMOUNTED,
        Intent.ACTION_MEDIA_CHECKING,
        Intent.ACTION_MEDIA_MOUNTED,
        Intent.ACTION_MEDIA_EJECT,
        Intent.ACTION_MEDIA_UNMOUNTABLE,
        Intent.ACTION_MEDIA_REMOVED,
        Intent.ACTION_MEDIA_BAD_REMOVAL,
        Intent.ACTION_MEDIA_SCANNER_STARTED,
        Intent.ACTION_MEDIA_SCANNER_FINISHED,
        ACTION_MEDIA_SCANNER_PRESCAN_FINISHED
    )
}
