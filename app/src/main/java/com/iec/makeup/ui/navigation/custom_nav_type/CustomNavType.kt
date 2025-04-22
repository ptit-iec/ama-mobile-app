package com.iec.makeup.ui.navigation.custom_nav_type

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import com.iec.makeup.core.model.ui.MakeUpTemplateLayout
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object CustomNavType {
    val MakeUpTemplateLayoutNavType = object: NavType<MakeUpTemplateLayout>(false){
        override fun get(bundle: Bundle, key: String): MakeUpTemplateLayout? {
            return Json.decodeFromString(bundle.getString(key) ?: return null)
        }

        override fun parseValue(value: String): MakeUpTemplateLayout {
            return Json.decodeFromString(Uri.decode(value))
        }

        override fun put(bundle: Bundle, key: String, value: MakeUpTemplateLayout) {
            bundle.putString(key, Json.encodeToString(value))
        }

        override fun serializeAsValue(value: MakeUpTemplateLayout): String {
            return Uri.encode(Json.encodeToString(value))
        }
    }
}