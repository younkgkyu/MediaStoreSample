package com.example.mediatestapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.mediatestapp.databinding.ActivityAudioTestBinding
import com.example.mediatestapp.listener.ICustomOnClickListener


class AudioTestActivity : AppCompatActivity(), ICustomOnClickListener {

    private lateinit var rootView: ActivityAudioTestBinding

    companion object {
        private val LOG_TAG = AudioTestActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rootView = DataBindingUtil.setContentView(this, R.layout.activity_audio_test)

        initUI()
    }

    private fun initUI() {

        rootView.iCustomOnClickListener = this
        rootView.lifecycleOwner = this

    }

    override fun onClick(position: Int) {
        when(position) {
            0 -> {
                onBackPressedDispatcher.onBackPressed()
            }
            1 -> {
                Toast.makeText(applicationContext, "Bt Call STart", Toast.LENGTH_SHORT).show()
            }
            2 -> {
                Toast.makeText(applicationContext, "Bt Call Stop", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun startBtCall() {
//        val oemAudioManager = OemAudioManager(applicationContext)
//        val audioManager = applicationContext.getSystemService(Context.AUDIO_SERVICE) as AudioManager
//        val device = oemAudioManager.getAudioDeviceInfoForHwSource(OemAudioManager.HW_SOURCE_BLUETOOTH_CALL)
//        val micModeAttrForBtCall = MicModeAttributes.Builder()
//            .setMicMode(MicModeAttributes.MIC_MODE_BLUETOOTH_CALL)
//            .setSamplingRate(16000)
//            .build()
//
//        // BT Call setMicMode
//        oemAudioManager.micMode = micModeAttrForBtCall
//
//        // BT Call AudioModeAttributes 생성
//        val audioModeAttrForBtCall = AudioModeAttributes.Builder()
//            .setAudioMode(AudioModeAttributes.AUDIO_MODE_BT_HANDSFREE)
//            .setSourceType(AudioModeAttributes.SOURCE_TYPE_BLUETOOTH_CALL)
//            .setSamplingRate(16000)
//            .build()
//
//        // BT Call  HwAudiosource 생성(USAGE_VOICE_COMMUNICATION)
//        val mHwAudioSourcecvoicecom = HwAudioSource.Builder()
//            .setAudioDeviceInfo(device)
//            .setAudioAttributes(
//                AudioAttributes.Builder()
//                    .setUsage(AudioAttributes.USAGE_VOICE_COMMUNICATION) // For volume group
//                    .build()
//            )
//            .build()
//
//        // BT Call AudioFocus 요청
//        val res1: Int = audioManager.requestAudioFocus(focusRequestcall)
//        synchronized(focusLock) {
//            if (res1 == AudioManager.AUDIOFOCUS_REQUEST_FAILED) {
//                // ...
//            } else if (res1 == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
//                // BT Call setAudioMode and HwAudiosource Start
//                oemAudioManager.audioMode = audioModeAttrForBtCall
//                mHwAudioSourcecvoicecom.start()
//            } else if (res1 == AudioManager.AUDIOFOCUS_REQUEST_DELAYED) {
//                // ...
//            }
//        }
    }
}