package com.iec.makeup.ui.features.home


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowCircleRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.iec.makeup.R
import com.iec.makeup.data.remote.dto.toMakeUpTemplateCategory
import com.iec.makeup.ui.LocalAppState
import com.iec.makeup.ui.features.home.components.AutoScrollingHorizontalCardList
import com.iec.makeup.ui.features.home.components.MakeUpStyleLayout
import com.iec.makeup.ui.features.home.components.StunningRoundedCard
import com.iec.makeup.ui.features.home.components.TopAppBar
import com.iec.makeup.ui.features.home.components.getSampleCardData
import com.iec.makeup.ui.features.home.helpers.OrderStatusType
import com.iec.makeup.ui.theme.ColorDB7093
import com.iec.makeup.ui.theme.ColorFAF9F9
import com.iec.makeup.ui.theme.ColorFF69B4
import com.iec.makeup.ui.theme.onPrimaryColor


/*
 - Later implement of MVI architecture, just manage state directly on screen.
 */


@Composable
fun HomeScreen(
    navToNotification: () -> Unit = {},
    navToSearch: () -> Unit = {},
    navToAllMakeUpArtist: () -> Unit = {},
    navToPersonalInfo: (String) -> Unit = {},
    navToChatting: () -> Unit = {},
    navToAllTemplate: (String, List<String>) -> Unit,
    navToAI: () -> Unit = {}
) {
    val context = LocalContext.current
    val viewModel: HomeScreenVM = hiltViewModel()
    val state = viewModel.state.collectAsStateWithLifecycle()
    val effect = viewModel.effect.collectAsStateWithLifecycle(initialValue = null)
    val appState = LocalAppState.current
    AuraBeautyApp(
        navToNotification = navToNotification,
        navToSearch = navToSearch,
        navToAllMakeUp = navToAllMakeUpArtist,
        navToPersonalInfo = navToPersonalInfo,
        navToChatting = navToChatting,
        navToAllTemplate = navToAllTemplate,
        navToAI = navToAI,
        state = state.value
    )
    appState.setLoading(state.value.isLoading)
}


@Composable
fun AuraBeautyApp(
    navToNotification: () -> Unit = {},
    navToSearch: () -> Unit = {},
    navToAllMakeUp: () -> Unit = {},
    navToPersonalInfo: (String) -> Unit = {},
    navToChatting: () -> Unit = {},
    navToAllTemplate: (String, List<String>) -> Unit = { _, _ -> },
    state: HomeScreenState = HomeScreenState(),
    navToAI: () -> Unit = {}
) {
    val scrollview = rememberScrollState()
    val orderChipSelected = remember { mutableStateOf<OrderStatusType>(OrderStatusType.TO_RECEIVE) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(
                colors = listOf(
                    Color(0xFFFFDCDF),
                    Color.White
                )
            ),)
            .verticalScroll(
                scrollview
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            // Top Bar
            TopAppBar(
                showNotifications = navToNotification,
                showChat = navToChatting,
                showSearch = navToSearch,
                image = state.userProfile?.avatar
                    ?: "https://blog.maika.ai/wp-content/uploads/2024/02/anh-meo-meme-2.jpg",
                name = state.userProfile?.name ?: "User"
            )
            // Content
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                AutoScrollingHorizontalCardList(
                    items = getSampleCardData()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.makeup_layout),
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
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
                                modifier = Modifier.clickable {
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
                        fontSize = 16.sp,
                        modifier = Modifier.padding(top = 12.dp, bottom = 12.dp)
                    )
                    Text(
                        text = stringResource(R.string.see_all),
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        color = onPrimaryColor,
                        style = TextStyle(
                            textDecoration = TextDecoration.Underline
                        ),
                        modifier = Modifier
                            .padding(top = 12.dp, bottom = 12.dp)
                            .clickable {
                                navToAllMakeUp()
                            }
                    )
                }
                val experts = state.listExpert
                LazyRow {
                    items(experts.size) { index ->
                        StunningRoundedCard(
                            item = experts[index],
                            onButtonClick = {
                            },
                            onItemClick = {
                                navToPersonalInfo(it)
                            }
                        )
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



