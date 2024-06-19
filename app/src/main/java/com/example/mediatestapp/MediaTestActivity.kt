package com.example.mediatestapp

import android.Manifest
import android.content.ContentResolver
import android.content.pm.PackageManager
import android.database.ContentObserver
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mediatestapp.adapter.IAdapterListener
import com.example.mediatestapp.adapter.MediaListAdapter
import com.example.mediatestapp.adapter.MediaQueryAdapter
import com.example.mediatestapp.adapter.MediaVolumeAdapter
import com.example.mediatestapp.databinding.ActivityMediaTestBinding
import com.example.mediatestapp.listener.ICustomOnClickListener


class MediaTestActivity : AppCompatActivity(), IAdapterListener, ICustomOnClickListener {

    private lateinit var rootView: ActivityMediaTestBinding
    private lateinit var volumeAdapter: MediaVolumeAdapter
    private lateinit var mediaListAdapter: MediaListAdapter
    private lateinit var mediaQueryAdapter: MediaQueryAdapter
    private val mediaContentObserver = object : ContentObserver(Handler(Looper.getMainLooper())) {
        override fun onChange(selfChange: Boolean, uri: Uri?) {
            super.onChange(selfChange, uri)
            rootView.tvInfo.text = uri.toString()
        }
    }
    private var volumeName: String? = null
    private var queryType = MediaQueryType.AUDIO

    companion object {
        private const val READ_EXTERNAL_PERMISSION_CODE = 1000;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rootView = DataBindingUtil.setContentView(this, R.layout.activity_media_test)

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

        rootView.iCustomOnClickListener = this
        rootView.lifecycleOwner = this

        applicationContext.contentResolver.registerContentObserver(
            MediaStore.AUTHORITY_URI,
            true,
            mediaContentObserver
        )

        rootView.tvMain.text = "Media Test App"
        volumeAdapter = MediaVolumeAdapter(this@MediaTestActivity, mutableListOf())
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

        mediaQueryAdapter = MediaQueryAdapter(
            this@MediaTestActivity,
            mutableListOf(MediaQueryType.AUDIO, MediaQueryType.VIDEO, MediaQueryType.IMAGE, MediaQueryType.FILE)
        )
        rootView.rvQueryType.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            adapter = mediaQueryAdapter
        }
    }

    override fun onItemClick(data: Any) {
        when {
            data is String -> {
                rootView.tvMain.text = "${data}:${queryType.type}"
                volumeName = data
                checkReadExternalPermission()
            }
            data is MediaQueryType -> {
                rootView.tvMain.text = "${volumeName ?: ""}:${data.type}"
                queryType = data
                volumeName?.let {
                    checkReadExternalPermission()
                }
            }
        }
    }

    private fun queryImage() {
        volumeName?.let { volumeName ->
            val volumeAudioUri: Uri = MediaStore.Files.getContentUri(volumeName)
            val projection = arrayOf(MediaStore.Files.FileColumns._ID, MediaStore.Files.FileColumns.TITLE, MediaStore.Files.FileColumns.MEDIA_TYPE)

            val selectionBundle = bundleOf(
                ContentResolver.QUERY_ARG_OFFSET to 0,
                ContentResolver.QUERY_ARG_LIMIT to 1000,
                ContentResolver.QUERY_ARG_SORT_COLUMNS to arrayOf(MediaStore.Files.FileColumns.DATE_MODIFIED),
                ContentResolver.QUERY_ARG_SORT_DIRECTION to ContentResolver.QUERY_SORT_DIRECTION_DESCENDING,
                ContentResolver.QUERY_ARG_SQL_SELECTION to "${MediaStore.Files.FileColumns.MEDIA_TYPE}=?",
                ContentResolver.QUERY_ARG_SQL_SELECTION_ARGS to mutableListOf(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE.toString()).toTypedArray()
            )

            Log.d("MediaTest", "uri : $volumeAudioUri")

            val cursor: Cursor? = contentResolver.query(volumeAudioUri, projection, selectionBundle, null)

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

    private fun queryVideo() {
        volumeName?.let { volumeName ->
            val volumeAudioUri: Uri = MediaStore.Files.getContentUri(volumeName)
            val projection = arrayOf(MediaStore.Files.FileColumns._ID, MediaStore.Files.FileColumns.TITLE, MediaStore.Files.FileColumns.MEDIA_TYPE)

            val selectionBundle = bundleOf(
                ContentResolver.QUERY_ARG_OFFSET to 0,
                ContentResolver.QUERY_ARG_LIMIT to 1000,
                ContentResolver.QUERY_ARG_SORT_COLUMNS to arrayOf(MediaStore.Files.FileColumns.DATE_MODIFIED),
                ContentResolver.QUERY_ARG_SORT_DIRECTION to ContentResolver.QUERY_SORT_DIRECTION_DESCENDING,
                ContentResolver.QUERY_ARG_SQL_SELECTION to "${MediaStore.Files.FileColumns.MEDIA_TYPE}=?",
                ContentResolver.QUERY_ARG_SQL_SELECTION_ARGS to mutableListOf(MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO.toString()).toTypedArray()
            )

            Log.d("MediaTest", "uri : $volumeAudioUri")

            val cursor: Cursor? = contentResolver.query(volumeAudioUri, projection, selectionBundle, null)

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

    private fun queryFile() {
        volumeName?.let { volumeName ->
            val volumeAudioUri: Uri = MediaStore.Files.getContentUri(volumeName)
            val projection = arrayOf(MediaStore.Files.FileColumns._ID, MediaStore.Files.FileColumns.TITLE, MediaStore.Files.FileColumns.MEDIA_TYPE)

            val selectionBundle = bundleOf(
                ContentResolver.QUERY_ARG_OFFSET to 0,
                ContentResolver.QUERY_ARG_LIMIT to 1000,
                ContentResolver.QUERY_ARG_SORT_COLUMNS to arrayOf(MediaStore.Files.FileColumns.DATE_MODIFIED),
                ContentResolver.QUERY_ARG_SORT_DIRECTION to ContentResolver.QUERY_SORT_DIRECTION_DESCENDING,
                ContentResolver.QUERY_ARG_SQL_SELECTION to "${MediaStore.Files.FileColumns.MEDIA_TYPE}=?",
                ContentResolver.QUERY_ARG_SQL_SELECTION_ARGS to mutableListOf(MediaStore.Files.FileColumns.MEDIA_TYPE_AUDIO.toString()).toTypedArray()
            )

            Log.d("MediaTest", "uri : $volumeAudioUri")

            val cursor: Cursor? = contentResolver.query(volumeAudioUri, projection, /*selectionBundle*/null, null)

            val audioList = mutableListOf<String>()
            cursor?.use {
                rootView.tvInfo.text = cursor.count.toString()
                if (cursor.count <= 0) return
                it.moveToFirst()
                do {
                    val id = it.getLong(it.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID))
                    val title = it.getString(it.getColumnIndexOrThrow(MediaStore.Files.FileColumns.TITLE))
                    val mimeType = it.getString(it.getColumnIndexOrThrow(MediaStore.Files.FileColumns.MEDIA_TYPE))
                    audioList.add("${title}:${mimeType}")
                } while (it.moveToNext())
            }
            mediaListAdapter.updateDataChanged(audioList)
        }
    }

    private fun queryData() {
        when(queryType) {
            MediaQueryType.AUDIO -> {
                queryAudio()
            }
            MediaQueryType.VIDEO -> {
                queryVideo()
            }
            MediaQueryType.IMAGE -> {
                queryImage()
            }
            MediaQueryType.FILE -> {
                queryFile()
            }
        }
    }

    private fun queryAudio() {
        volumeName?.let { volumeName ->
            val volumeAudioUri: Uri = MediaStore.Files.getContentUri(volumeName)
            val projection = arrayOf(MediaStore.Files.FileColumns._ID, MediaStore.Files.FileColumns.TITLE, MediaStore.Files.FileColumns.MEDIA_TYPE)

            val selectionBundle = bundleOf(
                ContentResolver.QUERY_ARG_OFFSET to 0,
                ContentResolver.QUERY_ARG_LIMIT to 1000,
                ContentResolver.QUERY_ARG_SORT_COLUMNS to arrayOf(MediaStore.Files.FileColumns.DATE_MODIFIED),
                ContentResolver.QUERY_ARG_SORT_DIRECTION to ContentResolver.QUERY_SORT_DIRECTION_DESCENDING,
                ContentResolver.QUERY_ARG_SQL_SELECTION to "${MediaStore.Files.FileColumns.MEDIA_TYPE}=?",
                ContentResolver.QUERY_ARG_SQL_SELECTION_ARGS to mutableListOf(MediaStore.Files.FileColumns.MEDIA_TYPE_AUDIO.toString()).toTypedArray()
            )

            Log.d("MediaTest", "uri : $volumeAudioUri")

            val cursor: Cursor? = contentResolver.query(volumeAudioUri, projection, selectionBundle, null)

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
                queryData()
            } else {
                requestReadExternalPermission()
            }
        } else {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                queryData()
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
                    queryData()
                } else {
                    //do noting
                }
            }
        }
    }

    override fun onClick(position: Int) {
        when(position) {
            0 -> onBackPressedDispatcher.onBackPressed()
        }
    }
}