package com.iec.makeup.core.network

import android.util.Log
import com.iec.makeup.core.utils.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okhttp3.logging.HttpLoggingInterceptor
import okio.ByteString
import java.time.Duration
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.math.log


data class WebSocketState(
    val isConnected: Boolean = false,
)


class WebSocketClient @Inject constructor(
) {

    private val scope by lazy {
        CoroutineScope(Dispatchers.IO) + CoroutineExceptionHandler { _, exception ->
            Log.d("WebSocketClient", exception.toString())
        }

    }
    val logger = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    private val client: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .readTimeout(
                READ_TIMEOUT,
                TimeUnit.MILLISECONDS
            ) // Important for long-lived connections
            .addInterceptor(logger)
            .connectTimeout(30, TimeUnit.SECONDS)
            .pingInterval(Duration.ofSeconds(PING_PONG_INTERVAL))
            .retryOnConnectionFailure(true)
            .build();
    }

    private var _webSocket: WebSocket? = null

    private val _incomingMessages = MutableSharedFlow<String>(
        replay = 3,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    // Expose as read-only SharedFlow
    val incomingMessages: SharedFlow<String> = _incomingMessages.asSharedFlow()

    private val webSocketListener by lazy {
        WebSocketListenerImpl()
    }


    var webSocketState = WebSocketState(isConnected = false)


    inner class WebSocketListenerImpl : WebSocketListener() {

        override fun onOpen(webSocket: WebSocket, response: okhttp3.Response) {
            super.onOpen(webSocket, response)
            webSocketState = webSocketState.copy(isConnected = true)
            Log.d("WebSocket", "WebSocket opened")
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            super.onMessage(webSocket, text)
            scope.launch {
                _incomingMessages.emit(text)
            }
            Log.d("WebSocket", "Received message: $text")
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            super.onClosing(webSocket, code, reason)
            // Handle closing the connection
        }

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            super.onClosed(webSocket, code, reason)
            // Handle closed connection
            webSocketState = webSocketState.copy(isConnected = false)
            Log.d("WebSocket", "WebSocket closed")
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: okhttp3.Response?) {
            super.onFailure(webSocket, t, response)
            // Handle failure
            _webSocket = null
            Log.e("WebSocket", "WebSocket failure", t)

        }

        override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
            super.onMessage(webSocket, bytes)
            // Handle binary messages
        }

    }


    fun startWebSocket(endpoint: String) {
        val request = Request.Builder()
            .url("ws://113.22.56.109:3457/chat")
            .header("Accept-Encoding", "identity")
            .build()
        try {
            _webSocket = client.newWebSocket(
                request, webSocketListener
            )
        } catch (e: Exception) {
            Log.e("WebSocket", "Error starting WebSocket", e)
        }
    }

    fun sendMessage(message: String): Boolean {
        try {
            return _webSocket?.send(message) ?: false
        } catch (e: Exception) {
            Log.e("WebSocket", "Error sending message", e)
        }
        return false
    }

    fun closeWebSocket() {
        if (_webSocket != null) {
            try {
                _webSocket?.close(1000, "Goodbye!")
                if (scope.isActive) {
                    scope.cancel()
                }
                Log.d("WebSocket", "WebSocket closed")
            } catch (e: Exception) {
                Log.e("WebSocket", "Error closing WebSocket", e)
            }
        }
    }

    companion object {
        const val READ_TIMEOUT = 10000L
        const val PING_PONG_INTERVAL = 5L
        const val CHAT_ROOM = "group"
    }
}