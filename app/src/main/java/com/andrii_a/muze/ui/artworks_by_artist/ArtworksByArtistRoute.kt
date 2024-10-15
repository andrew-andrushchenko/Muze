package com.andrii_a.muze.ui.artworks_by_artist

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.andrii_a.muze.ui.artwork_detail.navigateToArtworkDetail
import com.andrii_a.muze.ui.navigation.Screen

fun NavGraphBuilder.artworksByArtistRoute(
    navController: NavController
) {
    composable(
        route = "${Screen.ArtworksByArtist.route}/{artistId}/{artistName}",
        arguments = listOf(
            navArgument("artistId") {
                type = NavType.IntType
                nullable = false
            },
            navArgument("artistName") {
                type = NavType.StringType
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

        val artistName = navBackStackEntry.arguments?.getString("artistName").orEmpty()

        val viewModel: ArtworksByArtistViewModel = hiltViewModel()

        ArtworksByArtistScreen(
            artistName = artistName,
            artworksFlow = viewModel.artworks,
            onArtworkSelected = navController::navigateToArtworkDetail,
            navigateBack = navController::navigateUp
        )
    }
}

fun NavController.navigateToArtworksByArtist(artistId: Int, artistName: String) =
    this.navigate("${Screen.ArtworksByArtist.route}/$artistId/$artistName")