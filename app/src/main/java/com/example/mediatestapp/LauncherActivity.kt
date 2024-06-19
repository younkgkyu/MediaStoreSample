package com.example.mediatestapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.mediatestapp.databinding.ActivityLauncherBinding
import com.example.mediatestapp.listener.ICustomOnClickListener
import com.google.android.material.snackbar.Snackbar


class LauncherActivity : AppCompatActivity(), ICustomOnClickListener {

    private lateinit var rootView: ActivityLauncherBinding

    companion object {
        private val LOG_TAG = LauncherActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rootView = DataBindingUtil.setContentView(this, R.layout.activity_launcher)

        initUI()
    }

    private fun initUI() {

        rootView.iCustomOnClickListener = this
        rootView.lifecycleOwner = this

    }

    override fun onClick(position: Int) {
        when(position) {
            1 -> {
                startActivity(Intent(applicationContext, MediaTestActivity::class.java))
            }
            2 -> {
                startActivity(Intent(applicationContext, AudioTestActivity::class.java))
            }
        }
    }
}