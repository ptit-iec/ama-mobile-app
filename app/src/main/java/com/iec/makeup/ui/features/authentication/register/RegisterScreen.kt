package com.iec.makeup.ui.features.authentication.register

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockReset
import androidx.compose.material.icons.filled.Person2
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.iec.makeup.core.ui.AtomicLoadingDialog
import com.iec.makeup.core.ui.DialogCompose
import com.iec.makeup.core.utils.validatesEmailPattern
import com.iec.makeup.ui.features.authentication.login.LoginScreenEffect
import com.iec.makeup.ui.theme.Color33FF69B4
import com.iec.makeup.ui.theme.ColorDB7093
import com.iec.makeup.ui.theme.ColorFF69B4
import com.iec.makeup.ui.theme.ColorFFC1CC
import com.iec.makeup.ui.theme.ColorFFE4E1
import com.iec.makeup.ui.theme.ColorFFF0F5


@Composable
fun RegisterScreen(
    navBack: () -> Unit = {},
) {
    val context = LocalContext.current
    val viewModel: RegisterScreenVM = hiltViewModel()
    val state = viewModel.state.collectAsStateWithLifecycle()
    val effect = viewModel.effect.collectAsStateWithLifecycle(null)
    RegisterScreenStateless(
        state = state.value,
        inputUserName = viewModel::inputUsername,
        inputEmail = viewModel::inputEmail,
        inputPassword = viewModel::inputPassword,
        doSignUp = viewModel::doSignUp,
        errorDismiss = viewModel::hideErrorMessage,
        effect = effect.value,
        navBack = navBack
    )
}

@Composable
fun RegisterScreenStateless(
    state: RegisterState = RegisterState(),
    inputUserName: (String) -> Unit = {},
    inputEmail: (String) -> Unit = {},
    inputPassword: (String) -> Unit = {},
    doSignUp: () -> Unit = {},
    errorDismiss: () -> Unit = {},
    effect: RegisterScreenEffect?,
    navBack: () -> Unit = {}
){
    var isPasswordVisible by remember { mutableStateOf(true) }
    var isEmailError by remember { mutableStateOf<String?>(null) }
    var isPasswordNotIdentical by remember { mutableStateOf<String?>(null) }
    var isError by remember { mutableStateOf<String?>(null) }
    var confirmPassword by remember { mutableStateOf<String?>(null) }
    LaunchedEffect(key1 = effect) {
        if (effect != null && effect is RegisterScreenEffect.OnShowError) {
            isError = effect.message
        }
    }
    return Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        ColorFFC1CC, // Light Pink
                        ColorFFE4E1, // Misty Rose
                        ColorFFF0F5 // Lavender Blush
                    )
                )
            )
    ) {
        Box(
            modifier = Modifier.padding(16.dp).clickable {
                navBack()
            }
        ){
            Icon(
                imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = "Back",
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // App Logo/Title
            Text(
                text = "Register",
                style = MaterialTheme.typography.h3.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 40.sp
                ),
                color = ColorFF69B4, // Hot Pink
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Welcome Text
            Text(
                text = "Welcome to GowAura, new here, sign up now!",
                style = MaterialTheme.typography.subtitle1,
                color = ColorDB7093, // Pale Violet Red
                modifier = Modifier.padding(bottom = 16.dp)
            )
            OutlinedTextField(
                value = state.username ?: "",
                onValueChange = {
                    inputUserName(it)
                },
                label = { Text("Username") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = ColorFF69B4,
                    unfocusedTextColor = Color.Black,
                    focusedContainerColor = Color.Transparent,

                    ),
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person2,
                        contentDescription = "Email Icon",
                        tint = ColorFF69B4
                    )
                },

                )

            OutlinedTextField(
                value = state.email ?: "",
                onValueChange = {
                    isEmailError = null
                    inputEmail(it)
                },
                label = { Text("Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = ColorFF69B4,
                    unfocusedTextColor = Color.Black,
                    focusedContainerColor = Color.Transparent,

                    ),
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "Email Icon",
                        tint = ColorFF69B4
                    )
                },

                )
            if (isEmailError != null) {
                Text(
                    text = isEmailError ?: "",
                    color = Color.Red,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
            // Password Field
            OutlinedTextField(
                value = state.password ?: "",
                onValueChange = {
                    inputPassword(it)
                },
                label = { Text("Password") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                visualTransformation = if (isPasswordVisible) PasswordVisualTransformation() else VisualTransformation.None, // Show or hide password based on the value of isPasswordVisible=,
                colors = TextFieldDefaults.colors(
                    focusedTextColor = ColorFF69B4,
                    unfocusedTextColor = Color.Black,
                    focusedContainerColor = Color.Transparent,
                ), singleLine = true,
                shape = RoundedCornerShape(12.dp),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Password Icon",
                        tint = ColorFF69B4
                    )
                },
                trailingIcon = {
                    if (isPasswordVisible) {
                        Icon(
                            imageVector = Icons.Filled.VisibilityOff,
                            contentDescription = "Hide Password",
                            modifier = Modifier.clickable {
                                isPasswordVisible = !isPasswordVisible
                            }
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Visibility,
                            contentDescription = "Show Password",
                            modifier = Modifier.clickable {
                                isPasswordVisible = !isPasswordVisible
                            }
                        )
                    }
                }
            )
            OutlinedTextField(
                value = state.password ?: "",
                onValueChange = {
                    inputPassword(it)
                },
                label = { Text("Confirm Password") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                visualTransformation = if (isPasswordVisible) PasswordVisualTransformation() else VisualTransformation.None, // Show or hide password based on the value of isPasswordVisible=,
                colors = TextFieldDefaults.colors(
                    focusedTextColor = ColorFF69B4,
                    unfocusedTextColor = Color.Black,
                    focusedContainerColor = Color.Transparent,
                ), singleLine = true,
                shape = RoundedCornerShape(12.dp),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.LockReset,
                        contentDescription = "Password Icon",
                        tint = ColorFF69B4
                    )
                },
                trailingIcon = {
                    if (isPasswordVisible) {
                        Icon(
                            imageVector = Icons.Filled.VisibilityOff,
                            contentDescription = "Hide Password",
                            modifier = Modifier.clickable {
                                isPasswordVisible = !isPasswordVisible
                            }
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Visibility,
                            contentDescription = "Show Password",
                            modifier = Modifier.clickable {
                                isPasswordVisible = !isPasswordVisible
                            }
                        )
                    }
                }
            )
            // Login Button
            Button(
                onClick = {
                    if (!state.username.isNullOrEmpty() && state.username.validatesEmailPattern()) {
                        doSignUp()
                    } else if (state.username.isNullOrEmpty()) {
                        isEmailError = "Email is required"
                    } else {
                        isEmailError = "Invalid email "
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(top = 16.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ColorFF69B4
                )
            ) {
                Text(
                    text = "Sign up",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

        }

        // Decorative Element
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .size(100.dp)
                .background(
                    color = Color33FF69B4,
                    shape = CircleShape
                )
        )

        AnimatedVisibility(
            visible = state.isLoading,
            modifier = Modifier.fillMaxSize()
                .clickable {  },
            enter = slideInVertically(
                initialOffsetY = { fullHeight -> -fullHeight },
            ) + fadeIn(),
            exit = slideOutVertically(
                targetOffsetY = { fullHeight -> -fullHeight },
            ) + fadeOut(),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Black.copy(alpha = 0.8f)),
                contentAlignment = Alignment.Center
            ) {
                AtomicLoadingDialog()
            }
        }
        if(isError != null){
            DialogCompose(
                text = isError!!,
                onCloseAction = { errorDismiss() },
            )
        }
    }
}


@Preview
@Composable
private fun RegisterScreenPreview() {
    RegisterScreenStateless(
        effect = null
    )
}