package com.iec.makeup.core.model



enum class DarkThemeConfig {
    FOLLOW_SYSTEM,
    LIGHT,
    DARK
}

data class UserDefaultConfig (
    val darkThemeConfig: DarkThemeConfig
)