package com.iec.makeup.ui.features.ai_makeup.screen_talk_with_ai

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.iec.makeup.core.BaseViewModel
import com.iec.makeup.core.Reducer
import com.iec.makeup.core.utils.convertURItoMultipart
import com.iec.makeup.data.remote.api.ver2.ChatRequest
import com.iec.makeup.data.remote.api.ver2.TalkAIEndpoint
import com.iec.makeup.network.ver2.SSEClient
import com.iec.makeup.network.ver2.SSEHandler
import com.iec.makeup.network.ver2.SessionObject
import com.iec.makeup.ui.features.ai_makeup.business.AIScreenEvent
import com.iec.makeup.ui.features.ai_makeup.screen_talk_with_ai.model.DataEventAgentResponse
import com.iec.makeup.ui.features.ai_makeup.screen_talk_with_ai.model.DataEventStatus
import com.iec.makeup.ui.features.ai_makeup.screen_talk_with_ai.model.DataEventThinking
import com.iec.makeup.ui.features.ai_makeup.screen_talk_with_ai.model.EventDataClass
import com.launchdarkly.eventsource.MessageEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import io.ktor.http.parsing.ParseException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject


data class ScreenTalkViewState(
    val isLoading: Boolean = false,
    val userSpokenText: List<String> = emptyList(),
    val modifyBoxState: Boolean = false,
    val thinkingMode: String? = null,
    val statusAI: Set<String> = emptySet<String>(),
    val responseAI: String? = null,
    val imageUri: Uri? = null,
    val imageResult: String? = null
): Reducer.ViewState


sealed class ScreenTalkViewEvent : Reducer.ViewEvent {
    data class newUserText(val text: String): ScreenTalkViewEvent()
    data class onBoxModify(val show: Boolean): ScreenTalkViewEvent()
    data class connectSSEState(val state: String): ScreenTalkViewEvent()
    data class AIResponse(
        val response: String
    ): ScreenTalkViewEvent()
    data class AIThinking(
        val thinking: String
    ) : ScreenTalkViewEvent()
    data class AIStatus(
        val status: String
    ): ScreenTalkViewEvent()
    data object ClearStatus: ScreenTalkViewEvent()
    data class OnUploadImage(val uri: Uri) : ScreenTalkViewEvent()
    data class OnCaptureImage(val uri: Uri) : ScreenTalkViewEvent()
    data class onImageResult(val result: String?) : ScreenTalkViewEvent()
}

sealed class ScreenTalkViewEffect: Reducer.ViewEffect {
    data class ShowToast(val message: String): ScreenTalkViewEffect()
}


class ScreenTalkReducer : Reducer<ScreenTalkViewState, ScreenTalkViewEvent, ScreenTalkViewEffect> {
    override fun reduce(currentState: ScreenTalkViewState, event: ScreenTalkViewEvent): Pair<ScreenTalkViewState, ScreenTalkViewEffect?> {
        return when (event){
            is ScreenTalkViewEvent.newUserText -> {
                currentState.copy(userSpokenText = currentState.userSpokenText + event.text) to null
            }
            is ScreenTalkViewEvent.onBoxModify -> {
                currentState.copy(modifyBoxState = event.show) to null
            }

            is ScreenTalkViewEvent.connectSSEState -> {
                currentState to ScreenTalkViewEffect.ShowToast(event.state)
            }

            is ScreenTalkViewEvent.AIResponse -> {
                currentState.copy(responseAI = event.response) to null
            }
            is ScreenTalkViewEvent.AIStatus ->{
                currentState.copy(statusAI = currentState.statusAI + event.status) to null
            }
            is ScreenTalkViewEvent.AIThinking -> {
                currentState.copy(thinkingMode = event.thinking) to null
            }

            ScreenTalkViewEvent.ClearStatus -> {
                currentState.copy(statusAI = setOf()) to null
            }

            is ScreenTalkViewEvent.OnCaptureImage -> {
                currentState.copy(imageUri = event.uri) to null
            }
            is ScreenTalkViewEvent.OnUploadImage ->{
                currentState.copy(imageUri = event.uri) to null
            }

            is ScreenTalkViewEvent.onImageResult -> {
                currentState.copy(imageResult = event.result) to null
            }
        }
    }
}


@HiltViewModel
class ScreenTalkVM @Inject constructor(
    val sseClient: SSEClient,
    val talkAIEndpoint: TalkAIEndpoint,
    @ApplicationContext val context: Context
): BaseViewModel<ScreenTalkViewState, ScreenTalkViewEvent, ScreenTalkViewEffect>(
    initialState = ScreenTalkViewState(),
    reducer = ScreenTalkReducer()
), SSEHandler {


    private val jsonParser = Json {
        ignoreUnknownKeys = true
    }

    init {
        viewModelScope.launch {
            sseClient.initSse(
                this@ScreenTalkVM,
            ){
                Log.d("ScreenTalkVM", "initSse: ${it.message})")
            }
        }
    }

    fun onUserInput(message: String, bySpeaking: Boolean = true) {
        sendEvent(ScreenTalkViewEvent.newUserText(message))
        sendEvent(ScreenTalkViewEvent.onBoxModify(bySpeaking))
    }

    fun onUserCancel(){
        sendEvent(ScreenTalkViewEvent.onBoxModify(false))
    }

    fun onUserConfirm(message: String) {

        sendEvent(ScreenTalkViewEvent.newUserText(message))
        sendEvent(ScreenTalkViewEvent.ClearStatus)
        sendChatMessage(message)
        sendEvent(ScreenTalkViewEvent.onBoxModify(false))
    }
    private fun sendChatMessage(message: String){
        viewModelScope.launch {
            if(state.value.imageUri != null){
                val imagePath = state.value.imageUri
                try {
                    convertURItoMultipart(
                        uri = imagePath!!,
                        context = context,
                        fieldName = "file"
                    ).onEach { it ->
                        try {
                            talkAIEndpoint.sendImageAndChatMessage(
                                file = it,
                                message = message.toRequestBody("text/plain".toMediaTypeOrNull()),
                                sessionId = SessionObject.sessionID.toRequestBody("text/plain".toMediaTypeOrNull()),
                                showThinking = "true".toRequestBody("text/plain".toMediaTypeOrNull())
                            )
                        }catch (e: Exception){
                            Log.d("AIScreenVM", "submitImageToServer: ${e.message}")
                        }
                    }.launchIn(viewModelScope)
                }catch (e: Exception){
                    Log.d("AIScreenVM", "submitImageToServer: ${e.message}")
                }
            }
            else{
                talkAIEndpoint.sendNormalMessage(
                    ChatRequest(
                        message,
                        SessionObject.sessionID
                    )
                )
            }
        }
    }

    override fun onSSEConnectionOpened() {
        Log.d("ScreenTalkVM", "onSSEConnectionOpened")
        sendEvent(ScreenTalkViewEvent.connectSSEState("Connected"))
    }

    override fun onSSEConnectionClosed() {
        Log.d("ScreenTalkVM", "onSSEConnectionClosed")
    }
    fun uploadImage(uri: Uri) {
        sendEvent(ScreenTalkViewEvent.OnUploadImage(uri))
        sendEvent(ScreenTalkViewEvent.AIResponse("I've received your image. Please describe your makeup needs."))
    }
    fun captureImage(uri: Uri) {
        sendEvent(ScreenTalkViewEvent.OnCaptureImage(uri))
        sendEvent(ScreenTalkViewEvent.AIResponse("I've received your image. Please describe your makeup needs."))
    }
    override fun onSSEEventReceived(event: String, messageEvent: MessageEvent) {
        Log.d("ScreenTalkVM", "onSSEEventReceived: event: $event, data: ${messageEvent.data}")
        when(event){
            "agent_response" -> {
                val dataResponse = try {
                    jsonParser.decodeFromString<EventDataClass.EventAgentResponse>(
                        messageEvent.data
                    )
                }catch (e: SerializationException){
                    e.printStackTrace()
                    null
                }

                dataResponse?.let {
                    var response = it.data.response
                    extractURLFromMessage(response)?.let { url ->
                        if(url.contains("result")){
                            sendEvent(ScreenTalkViewEvent.onImageResult(url))
                            response = response.replace(url, "")
                        }
                    }
                    if(response.isNotEmpty()){
                        sendEvent(ScreenTalkViewEvent.AIResponse(cleanString(response)))
                    }
                }
            }
            "thinking" -> {
                val dataResponse = try {
                    jsonParser.decodeFromString<EventDataClass.EventThinking>(
                        messageEvent.data
                    )
                }catch (e: SerializationException){
                    e.printStackTrace()
                    null
                }
                dataResponse?.let {
                    sendEvent(ScreenTalkViewEvent.AIThinking(
                        it.data.content
                    ))
                }
            }
            "status" -> {
                val dataResponse = try {
                    jsonParser.decodeFromString<EventDataClass.EventStatus>(
                        messageEvent.data
                    )
                }catch (e: SerializationException){
                    e.printStackTrace()
                    null
                }
                dataResponse?.let {
                    it.data.details?.message?.let { message ->
                        sendEvent(ScreenTalkViewEvent.AIStatus(
                            message
                        ))
                    }
                    if(it.data.details?.result?.resultPath != null){
                        sendEvent(ScreenTalkViewEvent.onImageResult(
                            it.data.details!!.result!!.resultPath
                        ))
                    }
                }
            }
            "makeup_result" -> {
                val dataResponse = try {
                    jsonParser.decodeFromString<EventDataClass.EventResult>(
                        messageEvent.data
                    )
                }catch (e: SerializationException){
                    e.printStackTrace()
                    null
                }
                dataResponse?.let {
                    val result = it.data
                    result.imageUrl?.let { url ->
                        sendEvent(ScreenTalkViewEvent.onImageResult(
                            url
                        ))
                    }
                }
            }
        }
    }

    private fun cleanString(text: String): String {
        return text.replace(Regex("""[^\w\s]"""), "")
            .replace(Regex("""\s+"""), " ")
            .trim()
    }

    private fun extractURLFromMessage(message: String): String? {
        val regex = Regex("""\[[^\]]*]\(([^)]+)\)""")
        val match = regex.find(message)

        return match?.groupValues?.get(1)
    }
    override fun onSSEError(t: Throwable) {
        Log.d("ScreenTalkVM", "onSSEError: ${t.message}")
        sendEvent(ScreenTalkViewEvent.connectSSEState("Error: ${t.message}"))
    }

    fun resetImageResult(){
        sendEvent(ScreenTalkViewEvent.onImageResult(null))
    }

    fun downloadImage(url: String) {

    }

}