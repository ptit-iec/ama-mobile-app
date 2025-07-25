package com.iec.makeup.ui.features.home.screen_all_makeup_template

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.internal.StabilityInferred
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.iec.makeup.R
import com.iec.makeup.core.model.ui.MakeUpTemplateLayout
import com.iec.makeup.core.model.ui.mockMakeUpTemplateLayout
import com.iec.makeup.core.ui.AtomicLoadingDialog
import com.iec.makeup.core.ui.noRippleClickable
import com.iec.makeup.ui.LocalAppState
import com.iec.makeup.ui.features.home.screen_all_makeup_template.viewmodel.ScreenAllMakeUpTemplateEffect
import com.iec.makeup.ui.features.home.screen_all_makeup_template.viewmodel.ScreenAllMakeUpTemplateViewState
import com.iec.makeup.ui.features.home.screen_all_makeup_template.viewmodel.ScreenAllMakeupTemplateVM
import com.iec.makeup.ui.theme.ColorDB7093
import com.iec.makeup.ui.theme.onPrimaryColor
import com.iec.makeup.ui.theme.primaryColorV2
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun ScreenAllMakeupTemplateOfCategoryStateful(
    navBack: () -> Unit = {},
    categoryID: List<String> = emptyList(),
    title: String = "Layout Dự tiệc",
    navToTemplateDetail: (String) -> Unit = {},
) {

    val appState = LocalAppState.current
    val viewModel: ScreenAllMakeupTemplateVM = hiltViewModel()
    val state = viewModel.state.collectAsStateWithLifecycle()
    val effect = viewModel.effect.collectAsState(initial = null)

    LaunchedEffect(Unit) {
        viewModel.getInitialMakeUpTemplate(categoryID)
    }
    ScreenAllMakeupTemplateOfCategoryStateless(
        navBack = navBack,
        state = state.value,
        navToTemplateDetail = navToTemplateDetail,
        layout = title
    )

    appState.setLoading(state.value.isLoading)

}

@Composable
fun ScreenAllMakeupTemplateOfCategoryStateless(
    navBack: () -> Unit = {},
    state: ScreenAllMakeUpTemplateViewState = ScreenAllMakeUpTemplateViewState(),
    navToTemplateDetail: (String) -> Unit = {},
    layout: String = "Dự tiệc"
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color.White
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(16.dp),
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = "Back",
                tint = Color.DarkGray,
                modifier = Modifier
                    .fillMaxHeight()
                    .clickable {
                        navBack()
                    }
            )
            Text(
                text = layout,
                style = MaterialTheme.typography.titleLarge,
                color = Color.DarkGray,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
                    .background(
                        color = Color(0xFFFFD6D6),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }

        ScreenAllMakeupTemplateOfCategory(
            data = state.data,
            onClick = { templateID: String -> navToTemplateDetail(templateID) }
        )
    }
}

@Composable
fun ScreenAllMakeupTemplateOfCategory(
    data: List<MakeUpTemplateLayout>,
    onClick: (String) -> Unit = {}
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        verticalItemSpacing = 12.dp,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(data.size) { index ->
            Box(
                modifier = Modifier
                    .noRippleClickable {
                        onClick(Json.encodeToString(data[index]))
                    }
                    .clip(RoundedCornerShape(8.dp))
                    .width(100.dp)
                    .height(if(index == 0 || index % 4 == 0 || (index + 1) % 4 == 0) 180.dp else 240.dp)

            ) {
                // Replace with your actual image resource
                AsyncImage(
                    model = data[index].thumbnail,
                    error = painterResource(R.drawable.internet),
                    contentDescription = "Photo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}


@Preview
@Composable
fun RoundedCardPreview() {
    ScreenAllMakeupTemplateOfCategoryStateless()
}