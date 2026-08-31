package com.thenoman.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.thenoman.app.features.permissions.presentation.RequestPermissionsScreen
import com.thenoman.app.ui.theme.TheNoManTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TheNoManTheme {
                Scaffold() { innerPadding ->
                    RequestPermissionsScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}