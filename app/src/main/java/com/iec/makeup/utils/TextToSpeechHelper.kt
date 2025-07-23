package com.iec.makeup.utils

import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import java.util.Locale

class TextToSpeechHelper(
    val context: Context,
    val listener: TextToSpeech.OnInitListener
) {

    private val tts by lazy {
        TextToSpeech(context, listener)
    }
    fun initTTS(
        locale: Locale = Locale.US,
    ){
        tts.language = Locale("vn", "VN")
        tts.setSpeechRate(1f)
    }


    fun speak(text: String){
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    fun stop(){
        tts.stop()
    }

    private fun release(){
        tts.stop()
        tts.shutdown()
    }
}