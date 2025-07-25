package com.iec.makeup.ui.features.home


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
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
import com.iec.makeup.core.ui.noRippleClickable
import com.iec.makeup.data.remote.dto.toMakeUpTemplateCategory
import com.iec.makeup.ui.LocalAppState
import com.iec.makeup.ui.features.home.components.AutoScrollingHorizontalCardList
import com.iec.makeup.ui.features.home.components.ExpertCard
import com.iec.makeup.ui.features.home.components.MakeUpStyleLayout
import com.iec.makeup.ui.features.home.components.TopAppBar
import com.iec.makeup.ui.features.home.components.getSampleCardData
import com.iec.makeup.ui.theme.onPrimaryColor
import com.iec.makeup.ui.theme.onPrimaryColorV2


/*
 - Later implement of MVI architecture, just manage state directly on screen.
 */


@Composable
fun HomeScreen(
    navToNotification: () -> Unit = {},
    navToAllMakeUpArtist: () -> Unit = {},
    navToPersonalInfo: (String) -> Unit = {},
    navToAllTemplate: (String, List<String>) -> Unit,
) {
    val viewModel: HomeScreenVM = hiltViewModel()
    val state = viewModel.state.collectAsStateWithLifecycle()
    val appState = LocalAppState.current
    AuraBeautyApp(
        navToNotification = navToNotification,
        navToAllTemplate = navToAllTemplate,
        state = state.value,
        navToAllMakeUpArtist = navToAllMakeUpArtist,
        navToPersonalInfo = navToPersonalInfo
    )

    appState.setLoading(state.value.isLoading)
}


@Composable
fun AuraBeautyApp(
    navToNotification: () -> Unit = {},
    navToAllTemplate: (String, List<String>) -> Unit = { _, _ -> },
    state: HomeScreenState = HomeScreenState(),
    navToAllMakeUpArtist: () -> Unit = {},
    navToPersonalInfo: (String) -> Unit = {}
) {
    val scrollview = rememberScrollState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(
                colors = listOf(
                    Color(0xFFFF98A2),
                    Color.White,
                    Color.White
                )
            ))
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            // Top Bar
            Box(
                modifier = Modifier.padding(vertical = 8.dp)
            ){
                TopAppBar(
                    showNotifications = navToNotification,
                    image = state.userProfile?.avatar
                        ?: "https://blog.maika.ai/wp-content/uploads/2024/02/anh-meo-meme-2.jpg",
                    name = state.userProfile?.name ?: "User"
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(
                        horizontal = 8.dp
                    )
                ){
                    AutoScrollingHorizontalCardList(
                        items = getSampleCardData()
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.makeup_layout),
                        fontWeight = FontWeight.Medium,
                        fontSize = 20.sp,
                        color = onPrimaryColorV2,
                        modifier = Modifier.padding(top = 12.dp, bottom = 12.dp)
                    )
                }

                if (state.listMakeUpTemplateCategory.isEmpty()) {
                    Text(
                        text = "No Makeup Template Category",
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        color = Color.DarkGray,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp, bottom = 12.dp)
                    )
                } else {
                    LazyRow {
                        items(
                            count = state.listMakeUpTemplateCategory.size,
                        ) {
                            Box(
                                modifier = Modifier.noRippleClickable {
                                    navToAllTemplate(
                                        state.listMakeUpTemplateCategory[it].title ?: "",
                                        state.listMakeUpTemplateCategory[it].toMakeUpTemplateCategory().makeUpTemplateId
                                    )
                                }
                            ) {
                                MakeUpStyleLayout(item = state.listMakeUpTemplateCategory[it].toMakeUpTemplateCategory())
                            }
                        }
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.expert),
                        fontWeight = FontWeight.Medium,
                        color = onPrimaryColorV2,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(top = 12.dp, bottom = 12.dp)
                    )
                    Text(
                        text = stringResource(R.string.see_all),
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        color = onPrimaryColor,
                        modifier = Modifier
                            .padding(top = 12.dp, bottom = 12.dp)
                            .noRippleClickable {
                                navToAllMakeUpArtist()
                            }
                    )
                }
                val experts = state.listExpert
                LazyColumn(
                    state = rememberLazyListState(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(bottom = 16.dp)
                ) {
                    items(experts.size) { index ->
                        ExpertCard(
                            expert = experts[index],
                            onItemClick = {
                                navToPersonalInfo(it)
                            }
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            }
        }

    }
}


@Preview
@Composable
private fun HomeScreenPreview() {
    AuraBeautyApp()
}



