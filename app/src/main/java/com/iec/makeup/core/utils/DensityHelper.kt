package com.iec.makeup.core.utils

import android.content.Context
import androidx.compose.ui.unit.Dp


fun Dp.toPx(context: Context) = this.value * context.resources.displayMetrics.density
