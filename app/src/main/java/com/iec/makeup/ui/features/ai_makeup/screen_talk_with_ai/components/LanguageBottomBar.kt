package com.iec.makeup.ui.features.ai_makeup.screen_talk_with_ai.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import java.util.Locale


data class SupportLanguage(
    val language: String,
    val code: String
)

@Preview
@Composable
fun LanguageBottomBarPreview() {
    LanguageBottomBar(
        onDismissRequest = {},
        onSelectLang = {}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageBottomBar(
    onDismissRequest: () -> Unit,
    onSelectLang: (Locale) -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    var selectedIndex by remember { mutableStateOf(0) }

    val languages = listOf(
        SupportLanguage("English", "en"),
        SupportLanguage("Vietnamese", "vn"),
    )
    ModalBottomSheet(
        onDismissRequest = { onDismissRequest() },
        sheetState = sheetState
    ) {
        // Bottom Sheet Content
        Column(
            modifier = Modifier.fillMaxWidth().wrapContentHeight()
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            languages.forEachIndexed { index, language ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        // Use selectable to make the entire row clickable and accessible
                        .selectable(
                            selected = (index == selectedIndex),
                            onClick = { selectedIndex = index },
                            role = Role.RadioButton
                        )
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // The RadioButton itself
                    RadioButton(
                        selected = (index == selectedIndex),
                        onClick = null
                    )
                    Text(
                        text = language.language,
                        modifier = Modifier.padding(start = 16.dp),
                    )
                }
            }
            Button(
                onClick = {
                    onSelectLang(
                        Locale(languages[selectedIndex].code)
                    )
                    scope.launch {
                        sheetState.hide()
                    }
                },
                modifier = Modifier
            ) {
                Text("Apply")
            }
        }
    }
}
