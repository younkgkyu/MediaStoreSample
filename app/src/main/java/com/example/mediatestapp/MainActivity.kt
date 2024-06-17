package com.example.mediatestapp

import android.Manifest
import android.content.pm.PackageManager
import android.database.ContentObserver
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mediatestapp.adapter.IAdapterListener
import com.example.mediatestapp.adapter.MediaListAdapter
import com.example.mediatestapp.adapter.MediaVolumeAdapter
import com.example.mediatestapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), IAdapterListener {

    private lateinit var rootView: ActivityMainBinding
    private lateinit var volumeAdapter: MediaVolumeAdapter
    private lateinit var mediaListAdapter: MediaListAdapter
    private val mediaContentObserver = object : ContentObserver(Handler(Looper.getMainLooper())) {
        override fun onChange(selfChange: Boolean, uri: Uri?) {
            super.onChange(selfChange, uri)
            rootView.tvInfo.text = uri.toString()
        }
    }
    private var volumeName: String? = null

    companion object {
        private const val READ_EXTERNAL_PERMISSION_CODE = 1000;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rootView = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initUI()
        loadData()
    }

    override fun onDestroy() {
        super.onDestroy()
        applicationContext.contentResolver.unregisterContentObserver(mediaContentObserver)
    }

    private fun loadData() {
        val volumeList = MediaStore.getExternalVolumeNames(applicationContext)
        volumeAdapter.updateDataChanged(volumeList.toMutableList())
    }

    private fun initUI() {

        applicationContext.contentResolver.registerContentObserver(
            MediaStore.AUTHORITY_URI,
            true,
            mediaContentObserver
        )

        rootView.tvMain.text = "Media Test App"
        volumeAdapter = MediaVolumeAdapter(this@MainActivity, mutableListOf())
        rootView.rvVolume.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            adapter = volumeAdapter
        }

        mediaListAdapter = MediaListAdapter(mutableListOf())
        rootView.rvMediaList.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            adapter = mediaListAdapter
        }
    }

    override fun onItemClick(data: String) {
        rootView.tvMain.text = data
        volumeName = data
        checkReadExternalPermission()
    }

    private fun queryVideo() {
        volumeName?.let { volumeName ->
            val volumeAudioUri: Uri = MediaStore.Video.Media.getContentUri(volumeName)
            val projection = arrayOf(MediaStore.MediaColumns._ID, MediaStore.MediaColumns.TITLE)

            val cursor: Cursor? = contentResolver.query(volumeAudioUri, projection, null, null)

            val audioList = mutableListOf<String>()
            cursor?.use {
                rootView.tvInfo.text = cursor.count.toString()
                if (cursor.count <= 0) return
                it.moveToFirst()
                do {
                    val id = it.getLong(it.getColumnIndexOrThrow(MediaStore.MediaColumns._ID))
                    val title = it.getString(it.getColumnIndexOrThrow(MediaStore.MediaColumns.TITLE))
                    audioList.add(title)
                } while (it.moveToNext())
            }
            mediaListAdapter.updateDataChanged(audioList)
        }
    }

    private fun queryAudio() {
        volumeName?.let { volumeName ->
            val volumeAudioUri: Uri = MediaStore.Audio.Media.getContentUri(volumeName)
            val projection = arrayOf(MediaStore.MediaColumns._ID, MediaStore.MediaColumns.TITLE)

            val cursor: Cursor? = contentResolver.query(volumeAudioUri, projection, null, null)

            val audioList = mutableListOf<String>()
            cursor?.use {
                rootView.tvInfo.text = cursor.count.toString()
                if (cursor.count <= 0) return
                it.moveToFirst()
                do {
                    val id = it.getLong(it.getColumnIndexOrThrow(MediaStore.MediaColumns._ID))
                    val title = it.getString(it.getColumnIndexOrThrow(MediaStore.MediaColumns.TITLE))
                    audioList.add(title)
                } while (it.moveToNext())
            }
            mediaListAdapter.updateDataChanged(audioList)
        }
    }

    // 1. Camera 권한 확인
    private fun checkReadExternalPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_MEDIA_AUDIO,
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                queryAudio()
            } else {
                requestReadExternalPermission()
            }
        } else {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                queryAudio()
            } else {
                requestReadExternalPermission()
            }
        }
    }

    // 2. Camera 권한 요청
    private fun requestReadExternalPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.READ_MEDIA_AUDIO,
                    Manifest.permission.READ_MEDIA_VIDEO,
                    Manifest.permission.READ_MEDIA_IMAGES
                ),
                READ_EXTERNAL_PERMISSION_CODE
            )
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                READ_EXTERNAL_PERMISSION_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            READ_EXTERNAL_PERMISSION_CODE -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    queryAudio()
                } else {
                    //do noting
                }
            }
        }
    }
}