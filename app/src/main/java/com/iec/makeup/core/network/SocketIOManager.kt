package com.iec.makeup.core.network

import android.util.Log
import com.iec.makeup.core.model.Message
import com.iec.makeup.core.utils.Constants
import com.iec.makeup.data.remote.dto.MessageDTO
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Singleton


interface SocketStateListener {
    fun onSocketConnected()
    fun onSocketDisconnected()

}

@Singleton
class SocketIOManager @Inject constructor() {

    private var socketStateListener: SocketStateListener? = null
    private var mSocket: Socket? = null

    private val _incomingMessages = MutableSharedFlow<String>(
        replay = 3,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    // Expose as read-only SharedFlow
    val incomingMessages: SharedFlow<String> = _incomingMessages.asSharedFlow()


    fun initSocket(endpoint: String, events: List<String>) {
        try {
            val opts = IO.Options().apply {
                reconnection = true
                timeout = 10000
                transports = arrayOf("websocket")
            }
            mSocket = IO.socket("${Constants.WEB_SOCKET_URL}/${endpoint}", opts)
            mSocket?.on(Socket.EVENT_CONNECT) {
                socketStateListener?.onSocketConnected()
            }
            events.forEach(::addEvent)
            connect()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun addEvent(event: String) {
        mSocket?.on(event) { args ->
            val data = args[0] as JSONObject
            Log.d("SocketIO", "Received event: $event, data: $data")
            _incomingMessages.tryEmit(data.toString())
        }
    }

    fun setListener(listener: SocketStateListener) {
        socketStateListener = listener
    }

    private fun connect() {
        mSocket?.connect()
    }

    fun sendMessage(message: MessageDTO) {
        mSocket?.emit("message", JSONObject().apply {
            put("message", message.text)
            put("sender", message.sender)
            put("timestamp", message.timestamp)
        })
    }

    fun disconnect() {
        socketStateListener = null
        mSocket?.disconnect()
    }
}