package com.iec.makeup

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.iec.makeup.ui.MakeupApp
import com.iec.makeup.ui.MakeupAppState
import com.iec.makeup.ui.theme.MakeupAITheme
import dagger.hilt.android.AndroidEntryPoint
import java.net.HttpURLConnection
import java.net.URL


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val appState = MakeupAppState(navController)
            MakeupAITheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        MakeupApp(
                            navController = navController,
                            appState = appState
                        )
                    }
                }
            }
        }
    }
}

