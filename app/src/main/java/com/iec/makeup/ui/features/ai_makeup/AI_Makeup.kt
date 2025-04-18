package com.iec.makeup.ui.features.ai_makeup

import android.Manifest
import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.FileUpload
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.rememberAsyncImagePainter
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.iec.makeup.R
import com.iec.makeup.core.ui.AtomicLoadingDialog
import com.iec.makeup.core.ui.DialogCompose
import com.iec.makeup.core.ui.IECTextField
import com.iec.makeup.ui.features.ai_makeup.business.AIScreenEffect
import com.iec.makeup.ui.features.ai_makeup.business.AIScreenState
import com.iec.makeup.ui.features.ai_makeup.business.AIScreenVM
import com.iec.makeup.ui.features.home.components.LogoComponent
import com.iec.makeup.ui.theme.ColorDB7093
import com.iec.makeup.ui.theme.ColorFFC1CC
import com.iec.makeup.ui.theme.ColorFFE4E1
import java.io.File


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun VirtualScreen(
    navBack: () -> Unit = {},
    initialPrompts: String = "",
    randomList: List<String>? = null,
    navInstruction: () -> Unit = {},
    navInteraction: (String) -> Unit = {}
) {
    val context = LocalContext.current
    val viewModel: AIScreenVM = hiltViewModel()
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val state = viewModel.state.collectAsStateWithLifecycle()
    val effect = viewModel.effect.collectAsStateWithLifecycle(null)

    /*
     - Handle permission
     */
    val cameraPermissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)
    // Track if the permission request has been processed after user interaction
    var hasRequestedPermission by rememberSaveable { mutableStateOf(false) }
    var permissionRequestCompleted by rememberSaveable { mutableStateOf(false) }


    val file = File(context.cacheDir, "captured_image.jpg")
    val uri = FileProvider.getUriForFile(
        context,
        "${context.packageName}.provider",
        file
    )

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            viewModel.captureImage(uri ?: Uri.EMPTY)
        } else {
            viewModel.showError("Image capture failed")
        }
    }

    SideEffect {
        if (!permissionRequestCompleted) {
            cameraPermissionState.launchPermissionRequest()
            hasRequestedPermission = true
            permissionRequestCompleted = true
        }
    }
    LaunchedEffect(cameraPermissionState.status) {
        // Check if the permission state has changed after the request
        if (hasRequestedPermission) {
            permissionRequestCompleted = true
        }
    }

    LaunchedEffect(Unit) {
        if (initialPrompts.isNotEmpty()) {
            viewModel.onInitData(
                randomList ?: emptyList(),
                initialPrompts
            )
        }
    }
    LaunchedEffect(effect) {
        effect.value?.let {
            when (it) {
                is AIScreenEffect.ShowError -> {
                    errorMessage = it.message
                }

                is AIScreenEffect.ShowToast -> {
                }
            }
        }
    }
    AIMakeupScreen(
        initialPrompts,
        navInstruction,
        state = state.value,
        uploadImage = viewModel::uploadImage,
        onApply = navInteraction,
        inputDescription = viewModel::inputDescription,
        launchCamera = { cameraLauncher.launch(uri) },
        deletePicture = viewModel::onDeleteImage,
    )
    if (state.value.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            AtomicLoadingDialog()
        }
    }
    if (errorMessage != null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            DialogCompose(
                text = errorMessage!!,
                onCloseAction = {
                    errorMessage = null
                },
                positiveAction = {
                    errorMessage = null
                }
            )
        }
    }

}

@Preview
@Composable
private fun AIPreview() {
    AIMakeupScreen()
}

// Too lazy for separating these components :0
@Composable
fun AIMakeupScreen(
    initialPrompts: String = "",
    navInstruction: () -> Unit = {},
    state: AIScreenState = AIScreenState(),
    uploadImage: (uri: Uri) -> Unit = {},
    deletePicture: () -> Unit = {},
    inputDescription: (description: String) -> Unit = {},
    onApply: (String) -> Unit = {},
    launchCamera: () -> Unit = {}
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(bottom = 16.dp), // Light pink background for the entire app
        contentAlignment = Alignment.BottomCenter,
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 8.dp, end = 8.dp, top = 16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top bar with logo and manual icon
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                LogoComponent()

                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier.clickable {
                        navInstruction()
                    }
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Description,
                            contentDescription = "Description",
                            tint = ColorDB7093,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Manual",
                            color = ColorDB7093,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            // Main content card with shadow and better rounded corners
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                // Title with more appealing typography
                Text(
                    text = "AI Makeup - Ứng dụng tích hợp AI đưa cho bạn phong cách trang điểm phù hợp với bạn nhất",
                    color = ColorDB7093,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    letterSpacing = 0.5.sp
                )

                // Subtitle with improved readability
                Text(
                    text = "Vui lòng chụp ảnh và mô tả mong muốn kiểu makeup",
                    color = Color.Gray.copy(alpha = 0.9f),
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 8.dp, bottom = 28.dp)
                )

                // Before/After image with improved shadow and border
                Card(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .border(
                            width = 2.dp,
                            color = ColorFFC1CC,
                            shape = RoundedCornerShape(20.dp)
                        ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = ColorFFE4E1
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,

                        ) {
                        if (state.imageURL != null) {
                            if (state.imageURL == Uri.EMPTY) {
                                Image(
                                    painter = painterResource(id = R.drawable.pick2),
                                    contentDescription = "Before and after makeup comparison",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .height(200.dp)
                                )
                            } else {
                                Image(
                                    painter = rememberAsyncImagePainter(state.imageURL),
                                    contentDescription = "Captured Image",
                                    modifier = Modifier.height(400.dp),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.FileUpload,
                                contentDescription = "Play",
                                tint = Color.DarkGray,
                                modifier = Modifier.clickable {
                                    uploadImage(state.imageURL ?: Uri.EMPTY)
                                }
                            )
                            Icon(
                                imageVector = Icons.Outlined.CameraAlt,
                                contentDescription = "Play",
                                tint = Color.DarkGray,
                                modifier = Modifier.clickable {
                                    launchCamera()
                                }
                            )
                            Icon(
                                imageVector = Icons.Outlined.Delete,
                                contentDescription = "Play",
                                tint = Color.DarkGray,
                                modifier = Modifier.clickable {
                                    deletePicture()
                                }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Description card with improved styling
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = CardDefaults.cardColors(containerColor = ColorDB7093),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    // Description title with badge-like design
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color.White.copy(alpha = 0.2f),
                        modifier = Modifier.wrapContentWidth()
                    ) {
                        Text(
                            text = "Description",
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // User request card with improved text field
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(modifier = Modifier.padding(4.dp)) {
                            IECTextField(
                                placeholder = "Tôi muốn makeup tông hồng, da tôi da trắng, tóc vàng hôm nay tôi đi date với người yêu, tôi mặc 1 chiếc váy trắng với phong cách bánh bèo, trông nhẹ nhàng nữ tính",
                                value = state.requestDescription ?: "",
                                onValueChange = {
                                    inputDescription(it)
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(120.dp)
                                    .padding(4.dp)
                            )
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Shuffle,
                                    contentDescription = "Shuffle",
                                    tint = Color.DarkGray,
                                    modifier = Modifier
                                        .size(24.dp)
                                        .clickable {
                                        }
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Ngẫu nhiên",
                                    fontSize = 14.sp
                                )

                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(50.dp))

            // Start button with gradient and animation

        }
        Button(
            onClick = {
                onApply("")
            },
            modifier = Modifier
                .width(100.dp)
                .padding(vertical = 8.dp),

            colors = ButtonDefaults.buttonColors(
                containerColor = ColorFFC1CC
            ),
            shape = RoundedCornerShape(16.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Apply",
                    color = Color.White,
                    fontSize = 18.sp,
                    modifier = Modifier.fillMaxWidth(),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }

}