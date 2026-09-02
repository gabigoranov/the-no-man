package com.thenoman.app.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.compose.composable
import androidx.navigation.createGraph
import com.thenoman.app.features.home.presentation.HomeScreen
import com.thenoman.app.features.permissions.presentation.RequestPermissionsScreen
import kotlinx.serialization.Serializable

@Serializable
object RequestPermissions

@Serializable
object Home

fun createNavGraph(navController: NavController, arePermissionsGranted: Boolean): NavGraph {
    // Set up startDestination based on user permissions granted
    val startDestination = if (arePermissionsGranted) Home else RequestPermissions
    return navController.createGraph(startDestination) {
        composable<RequestPermissions> { RequestPermissionsScreen() { navController.navigate(route = Home) } }
        composable<Home> { HomeScreen() }
    }
}
