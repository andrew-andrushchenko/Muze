package com.andrii_a.muze.ui.artist_detail

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.andrii_a.muze.ui.artwork_detail.navigateToArtworkDetail
import com.andrii_a.muze.ui.artworks_by_artist.navigateToArtworksByArtist
import com.andrii_a.muze.ui.navigation.Screen

fun NavGraphBuilder.artistDetailRoute(
    navController: NavController
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
        val view = LocalView.current
        val shouldUseDarkIcons = !isSystemInDarkTheme()

        LaunchedEffect(key1 = true) {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = shouldUseDarkIcons
        }

        val artistId = navBackStackEntry.arguments?.getInt("artistId") ?: 1

        val viewModel: ArtistDetailViewModel = hiltViewModel()
        val loadResult = viewModel.loadResult.collectAsStateWithLifecycle()

        val artworksFlow = viewModel.artworks

        ArtistDetailScreen(
            artistId = artistId,
            loadResult = loadResult.value,
            artworksFlow = artworksFlow,
            navigateToArtistArtworks = navController::navigateToArtworksByArtist,
            navigateToArtworkDetail = navController::navigateToArtworkDetail,
            onRetryLoadingArtist = viewModel::getArtist,
            navigateBack = navController::navigateUp
        )
    }
}

fun NavController.navigateToArtistDetail(artistId: Int) =
    this.navigate("${Screen.ArtistDetail.route}/$artistId")