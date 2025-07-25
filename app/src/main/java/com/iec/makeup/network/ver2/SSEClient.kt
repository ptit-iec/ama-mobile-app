package com.iec.makeup.network.ver2

import android.os.Build
import android.util.Log
import com.launchdarkly.eventsource.EventHandler
import com.launchdarkly.eventsource.EventSource
import com.launchdarkly.eventsource.MessageEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.internal.closeQuietly
import java.net.URI
import java.util.UUID
import javax.inject.Inject
import kotlin.time.Duration


interface SSEHandler {
    fun onSSEConnectionOpened()
    fun onSSEConnectionClosed()
    fun onSSEEventReceived(event: String, messageEvent: MessageEvent)
    fun onSSEError(t: Throwable)
}


class SSEClient @Inject constructor() {

    private var sseHandlers: SSEHandler? = null
    private var eventSourceSse: EventSource? = null

    fun initSse(sseHandler: SSEHandler, errorCallback: (Throwable) -> Unit) {
        SessionObject.sessionID = "chat_${System.currentTimeMillis()}_${UUID.randomUUID()}"
        this.sseHandlers = sseHandler
        val eventHandler = sseHandlers?.let { DefaultEventHandler(it) }
        val baseUrl = BASE_URL_SSE
        val PATH = ROUTE_CHAT + SessionObject.sessionID
        try {
            eventSourceSse = EventSource.Builder(
                eventHandler, URI.create(baseUrl.plus(PATH))
            )
                .connectTimeout(java.time.Duration.ofSeconds(5))
                .backoffResetThreshold(java.time.Duration.ofSeconds(5))
                .build()

            eventSourceSse?.let {
                it.start()
            }
        } catch (e: Exception) {
            errorCallback(e)
        }
    }

    private class DefaultEventHandler(private val sseHandler: SSEHandler) : EventHandler {

        override fun onOpen() {
            sseHandler.onSSEConnectionOpened()
        }

        override fun onClosed() {
            sseHandler.onSSEConnectionClosed()
        }

        override fun onMessage(event: String, messageEvent: MessageEvent) {
            sseHandler.onSSEEventReceived(event,messageEvent)
        }

        override fun onError(t: Throwable) {
            sseHandler.onSSEError(t)
        }

        override fun onComment(comment: String) {
            Log.i("SSE_CONNECTION", comment)
        }
    }

    fun disconnect() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                eventSourceSse?.closeQuietly()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}