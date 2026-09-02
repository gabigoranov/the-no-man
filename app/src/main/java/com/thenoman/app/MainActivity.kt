package com.thenoman.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.thenoman.app.navigation.createNavGraph
import com.thenoman.app.ui.theme.TheNoManTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AppContents()
        }
    }
}

@Composable
private fun AppContents() {
    val navController = rememberNavController()
    val navGraph = remember(navController) {
        createNavGraph(navController, false)
    }

    TheNoManTheme {
        NavHost(navController, navGraph)
    }
}