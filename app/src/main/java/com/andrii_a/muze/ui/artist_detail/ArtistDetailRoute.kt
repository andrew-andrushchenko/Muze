package com.andrii_a.muze.ui.artist_detail

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
import com.andrii_a.muze.ui.artwork_detail.navigateToArtworkDetail
import com.andrii_a.muze.ui.navigation.Screen
import com.google.accompanist.systemuicontroller.SystemUiController

fun NavGraphBuilder.artistDetailRoute(
    navController: NavController,
    systemUiController: SystemUiController
) {
    composable(
        route = "${Screen.ArtistDetail.route}/{artistId}",
        arguments = listOf(
            navArgument("artistId") {
                type = NavType.IntType
                nullable = false
            }
        )
    ) { navBackStackEntry ->
        val systemBarsColor = Color.Transparent
        val areIconsDark = !isSystemInDarkTheme()

        LaunchedEffect(key1 = true) {
            systemUiController.setSystemBarsColor(
                color = systemBarsColor,
                darkIcons = areIconsDark
            )
        }

        val artistId = navBackStackEntry.arguments?.getInt("artistId") ?: 1

        val viewModel: ArtistDetailViewModel = hiltViewModel()
        val loadResult = viewModel.loadResult.collectAsStateWithLifecycle()

        val artworksFlow = viewModel.artworks

        ArtistDetailScreen(
            artistId = artistId,
            loadResult = loadResult.value,
            artworksFlow = artworksFlow,
            navigateToArtistArtworks = {},
            navigateToArtworkDetail = navController::navigateToArtworkDetail,
            onRetryLoadingArtist = viewModel::getArtist,
            navigateBack = navController::navigateUp
        )
    }
}

fun NavController.navigateToArtistDetail(artistId: Int) =
    this.navigate("${Screen.ArtistDetail.route}/$artistId")