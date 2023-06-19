package com.andrii_a.muze.ui.artworks

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.andrii_a.muze.ui.artwork_detail.navigateToArtworkDetail
import com.andrii_a.muze.ui.navigation.NavigationScreen
import com.google.accompanist.systemuicontroller.SystemUiController

fun NavGraphBuilder.artworksRoute(
    navController: NavController,
    systemUiController: SystemUiController
) {
    composable(route = NavigationScreen.Artworks.route) {
        val statusBarColor = Color.Transparent
        val navigationBarColor = Color.Transparent
        val isDark = isSystemInDarkTheme()

        LaunchedEffect(key1 = true) {
            systemUiController.setStatusBarColor(
                color = statusBarColor,
                darkIcons = !isDark
            )

            systemUiController.setNavigationBarColor(
                color = navigationBarColor,
                darkIcons = !isDark
            )
        }

        val viewModel: ArtworksViewModel = hiltViewModel()

        ArtworksScreen(
            artworksFlow = viewModel.artworks,
            onArtworkSelected = navController::navigateToArtworkDetail
        )
    }
}
