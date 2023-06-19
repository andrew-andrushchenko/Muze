package com.andrii_a.muze.ui.artwork_detail

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.andrii_a.muze.ui.navigation.Screen
import com.google.accompanist.systemuicontroller.SystemUiController

fun NavGraphBuilder.artworkDetailRoute(
    navController: NavController,
    systemUiController: SystemUiController
) {
    composable(
        route = "${Screen.ArtworkDetail.route}/{artworkId}",
        arguments = listOf(
            navArgument("artworkId") {
                type = NavType.IntType
                nullable = false
            }
        )
    ) {
        val statusBarColor = Color.Transparent
        val navigationBarColor = Color.Transparent

        LaunchedEffect(key1 = true) {
            systemUiController.setStatusBarColor(
                color = statusBarColor,
                darkIcons = false
            )

            systemUiController.setNavigationBarColor(
                color = navigationBarColor,
                darkIcons = false
            )
        }

        val viewModel: ArtworkDetailViewModel = hiltViewModel()
        val loadResult = viewModel.loadResult.collectAsStateWithLifecycle()

        ArtworkDetailScreen(
            loadResult = loadResult.value,
            navigateBack = navController::navigateUp
        )
    }
}

fun NavController.navigateToArtworkDetail(artworkId: Int) =
    this.navigate("${Screen.ArtworkDetail.route}/$artworkId")