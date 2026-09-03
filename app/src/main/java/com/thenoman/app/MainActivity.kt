package com.thenoman.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.thenoman.app.features.permissions.data.listOfRequiredPermissions
import com.thenoman.app.features.permissions.domain.PermissionChecker
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
    val context = LocalContext.current
    val navGraph = remember(navController) {
        val pc = PermissionChecker(context)
        createNavGraph(navController, listOfRequiredPermissions.all {
            pc.checkPermissionForIntent(it.permissionIntent)
        })
    }

    TheNoManTheme {
        NavHost(navController, navGraph)
    }
}