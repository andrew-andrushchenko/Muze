package com.andrii_a.muze.ui.artworks_by_artist

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.andrii_a.muze.ui.navigation.Screen

fun NavGraphBuilder.artworksByArtistRoute(
    navController: NavController
) {
    composable<Screen.ArtworksByArtist> { navBackStackEntry ->
        val view = LocalView.current
        val shouldUseDarkIcons = !isSystemInDarkTheme()

        LaunchedEffect(key1 = true) {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = shouldUseDarkIcons
        }

        val artistName = navBackStackEntry.toRoute<Screen.ArtworksByArtist>().artistName

        val viewModel: ArtworksByArtistViewModel = hiltViewModel()

        ArtworksByArtistScreen(
            artistName = artistName,
            artworksFlow = viewModel.artworks,
            onArtworkSelected = { navController.navigate(Screen.ArtworkDetail(it)) },
            navigateBack = navController::navigateUp
        )
    }
}
