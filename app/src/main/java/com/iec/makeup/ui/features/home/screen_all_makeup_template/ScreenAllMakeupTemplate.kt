package com.iec.makeup.ui.features.home.screen_all_makeup_template

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.iec.makeup.core.model.ui.MakeUpTemplateLayout
import com.iec.makeup.core.model.ui.mockMakeUpTemplateLayout
import com.iec.makeup.core.ui.AtomicLoadingDialog
import com.iec.makeup.ui.features.home.screen_all_makeup_template.viewmodel.ScreenAllMakeUpTemplateEffect
import com.iec.makeup.ui.features.home.screen_all_makeup_template.viewmodel.ScreenAllMakeupTemplateVM
import com.iec.makeup.ui.theme.ColorDB7093

@Composable
fun ScreenAllMakeupTemplateOfCategoryStateful(
    navBack: () -> Unit = {},
    categoryID: String = "",
    title: String = "Layout Dự tiệc",
    navToTemplateDetail: (String) -> Unit = {},
) {

    val viewModel: ScreenAllMakeupTemplateVM = hiltViewModel()
    val state = viewModel.state.collectAsStateWithLifecycle()
    val effect = viewModel.effect.collectAsState(initial = null)

    LaunchedEffect(key1 = effect.value) {
        if (effect.value is ScreenAllMakeUpTemplateEffect.Error) {

        }
    }

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
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            ColorDB7093,
                            Color.White
                        )
                    ),
                )
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        navBack()
                    }
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = title,
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    FilterButton(
                        text = "Tất cả",
                        selected = true,
                        modifier = Modifier.weight(1f)
                    )
                    FilterButton(
                        text = "Yêu thích",
                        selected = false,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        ScreenAllMakeupTemplateOfCategory(
            data = state.value.data,
            onClick = { templateID: String -> navToTemplateDetail(templateID) }
        )
    }

    if (state.value.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White.copy(alpha = 0.5f)),
            contentAlignment = Alignment.Center
        ) {
            AtomicLoadingDialog()
        }
    }
}


@Composable
fun ScreenAllMakeupTemplateOfCategory(
    data: List<MakeUpTemplateLayout> = mockMakeUpTemplateLayout,
    onClick: (String) -> Unit = {}
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        items(data.size) { index ->
            PhotoCard(
                image = data[index].image,
                isFavorite = data[index].isFavorite,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .clickable { onClick(data[index].id) }
            )
        }
    }
}

@Composable
fun FilterButton(
    text: String,
    selected: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        modifier = Modifier.padding(vertical = 12.dp)
    ) {
        Box(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
        ) {
            Text(
                text = text,
                fontSize = 12.sp,
                color = if (selected) Color(0xFFFF6B78) else Color.Gray,
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
            )
        }
    }
}

@Composable
fun PhotoCard(
    image: String,
    isFavorite: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .height(180.dp)
    ) {
        // Replace with your actual image resource
        AsyncImage(
            model = image,
            contentDescription = "Photo",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

    }
}

@Preview
@Composable
fun RoundedCardPreview() {
    ScreenAllMakeupTemplateOfCategoryStateful()
}