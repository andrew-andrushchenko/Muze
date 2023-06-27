package com.andrii_a.muze.ui.artwork_detail

import androidx.compose.foundation.isSystemInDarkTheme
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
    ) { navBackStackEntry ->
        val viewModel: ArtworkDetailViewModel = hiltViewModel()
        val loadResult = viewModel.loadResult.collectAsStateWithLifecycle()

        val systemBarsColor = Color.Transparent
        val areIconsDark = !isSystemInDarkTheme()

        LaunchedEffect(key1 = loadResult.value) {
            when (loadResult.value) {
                ArtworkLoadResult.Empty,
                ArtworkLoadResult.Error,
                ArtworkLoadResult.Loading -> {
                    systemUiController.setSystemBarsColor(
                        color = systemBarsColor,
                        darkIcons = areIconsDark
                    )
                }

                is ArtworkLoadResult.Success -> {
                    systemUiController.setSystemBarsColor(
                        color = systemBarsColor,
                        darkIcons = false
                    )
                }
            }
        }

        val artworkId = navBackStackEntry.arguments?.getInt("artworkId") ?: 1

        ArtworkDetailScreen(
            artworkId = artworkId,
            loadResult = loadResult.value,
            onRetryLoadingArtwork = viewModel::getArtwork,
            navigateBack = navController::navigateUp
        )
    }
}

fun NavController.navigateToArtworkDetail(artworkId: Int) =
    this.navigate("${Screen.ArtworkDetail.route}/$artworkId")