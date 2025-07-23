package com.iec.makeup.core.utils

import android.content.Context
import android.net.Uri
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File


fun convertURItoMultipart(uri: Uri, context: Context, fieldName: String = "makeup") =
    callbackFlow<MultipartBody.Part> {

        val inputStream = context.contentResolver.openInputStream(uri)

        val tempFile = File.createTempFile("upload", ".png", context.cacheDir)

        tempFile.outputStream().use { fileOut ->
            inputStream?.copyTo(fileOut)
        }

        val requestBody = tempFile.asRequestBody("image/webp".toMediaTypeOrNull())
        val file = inputStream?.let {
            MultipartBody.Part.createFormData(
                fieldName,
                uri.lastPathSegment,
                requestBody
            )
        }
        file?.let { trySend(it) }

        awaitClose{
            inputStream?.close()
            tempFile.delete()
        }

    }
        .flowOn(Dispatchers.IO)
        .catch {
            Log.d("Multipart", it.message ?: "Unknown error")
        }