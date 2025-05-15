package com.iec.makeup.ui.features.profiles.booking_history

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.iec.makeup.R
import com.iec.makeup.ui.LocalAppState
import com.iec.makeup.ui.features.profiles.booking_history.components.booked_screen.ScreenBookedAppointment
import com.iec.makeup.ui.features.profiles.booking_history.components.canceled_screen.ScreenCanceledBooking
import com.iec.makeup.ui.features.profiles.booking_history.components.finished_screen.ScreenFinishedBooking
import com.iec.makeup.ui.features.profiles.booking_history.components.in_progress_screen.ScreenInProgressBooking
import com.iec.makeup.ui.features.profiles.booking_history.model.HistoryCategory
import com.iec.makeup.ui.theme.ColorDB7093





@Composable
fun ScreenBookingHistoryStateful(
    navBack: () -> Unit
) {
    val viewModel : ScreenBookingHistoryVM = hiltViewModel()
    val state = viewModel.state.collectAsStateWithLifecycle()
    val effect = viewModel.effect.collectAsStateWithLifecycle(initialValue = null)
    val appState = LocalAppState.current
    ScreenBookingHistory(
        navBack = navBack,
        state = state.value
    )
    effect.value?.let {
        when(it){
            is ScreenBookingHistoryEffect.OnShowError -> {
            }
            is ScreenBookingHistoryEffect.OnNavigateToDetail -> {
            }
            is ScreenBookingHistoryEffect.OnShowToast -> {
            }
        }
    }
    appState.setLoading(state.value.isLoading)
}

@Composable
fun ScreenBookingHistory(
    navBack: () -> Unit = {},
    state: ScreenBookingHistoryState= ScreenBookingHistoryState(),
) {
    val selectedCategory =   remember {
        mutableStateOf<HistoryCategory>(
            HistoryCategory.PENDING
        )
    }
    val listCategory = HistoryCategory.entries.toList()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.White,
        topBar = {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBackIosNew,
                        contentDescription = "Back",
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxHeight()
                            .size(24.dp)
                            .clickable {
                                navBack()
                            },
                    )
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.booking_history),
                            modifier = Modifier.align(Alignment.Center),
                            textAlign = TextAlign.Center,
                            color = ColorDB7093,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    }

                }
                TabRow(
                    selectedTabIndex = listCategory.indexOf(selectedCategory.value),
                    modifier = Modifier.fillMaxWidth(),
                    indicator = {

                    }
                ) {
                    listCategory.forEachIndexed { _, category ->
                        val isSelected = category == selectedCategory.value
                        Box(
                            modifier = Modifier.background(Color.White).padding(bottom = 8.dp),
                            contentAlignment = Alignment.Center
                        ){
                            Text(
                                text = category.titleVN,
                                modifier = Modifier
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                                    .align(Alignment.Center)
                                    .clickable {
                                        selectedCategory.value = category
                                    },
                                fontSize = 14.sp,
                                textAlign = TextAlign.Center,
                                color = if (isSelected) ColorDB7093 else Color.Black.copy(alpha = 0.5f),
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            )
                        }
                    }

                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding( innerPadding  )
        ) {
            Box(
                modifier = Modifier
                    .padding(top = 8.dp)
            ){
                when(selectedCategory.value){
                    HistoryCategory.PENDING -> {
                        ScreenBookedAppointment(
                            data = state.data?.filter { it.state == HistoryCategory.PENDING } ?: emptyList(),
                        )
                    }
                    HistoryCategory.CONFIRMED -> {
                        ScreenInProgressBooking(
                            data = state.data?.filter { it.state == HistoryCategory.CONFIRMED } ?: emptyList(),
                        )
                    }
                    HistoryCategory.COMPLETED -> {
                        ScreenFinishedBooking(
                            data = state.data?.filter { it.state == HistoryCategory.COMPLETED } ?: emptyList(),
                        )
                    }
                    HistoryCategory.CANCELED -> {
                        ScreenCanceledBooking(
                            data = state.data?.filter { it.state == HistoryCategory.CANCELED } ?: emptyList(),
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ScreenPreview() {
    ScreenBookingHistory()
}