package com.iec.makeup.utils

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import java.util.Locale


class SpeechToTextHelper(val context: Context) {

    private val speechRecognizer by lazy {
        SpeechRecognizer.createSpeechRecognizer(context)
    }

    private val intentLocale = Intent("android.speech.action.RECOGNIZE_SPEECH").apply {
        putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
    }

    fun startListening(
        onResult: (String) -> Unit
    ) {
        speechRecognizer.setRecognitionListener(object : RecognitionListener {
            override fun onResults(results: Bundle) {
                val matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (!matches.isNullOrEmpty()) {
                    val spokenText = matches.first()
                    onResult(spokenText)
                }
            }


            override fun onReadyForSpeech(params: Bundle) {}
            override fun onBeginningOfSpeech() {}
            override fun onRmsChanged(rmsdB: Float) {}
            override fun onBufferReceived(buffer: ByteArray) {}
            override fun onEndOfSpeech() {
                Log.d("SpeechToTextHelper", "End of speech")
            }
            override fun onError(error: Int) {
                if (error == SpeechRecognizer.ERROR_SPEECH_TIMEOUT) {
                    // Handle timeout error
                }
            }
            override fun onPartialResults(partialResults: Bundle) {}
            override fun onEvent(eventType: Int, params: Bundle) {}
        })
        speechRecognizer.startListening(intentLocale)
    }

}