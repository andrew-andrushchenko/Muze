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
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.andrii_a.muze.ui.navigation.Screen

fun NavGraphBuilder.artistDetailRoute(
    navController: NavController
) {
    composable<Screen.ArtistDetail> { navBackStackEntry ->
        val view = LocalView.current
        val shouldUseDarkIcons = !isSystemInDarkTheme()

        LaunchedEffect(key1 = true) {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars =
                shouldUseDarkIcons
        }

        val artistId = navBackStackEntry.toRoute<Screen.ArtistDetail>().artistId

        val viewModel: ArtistDetailViewModel = hiltViewModel()
        val loadResult = viewModel.loadResult.collectAsStateWithLifecycle()

        val artworksFlow = viewModel.artworks

        ArtistDetailScreen(
            artistId = artistId,
            loadResult = loadResult.value,
            artworksFlow = artworksFlow,
            navigateToArtistArtworks = { id, name -> navController.navigate(Screen.ArtworksByArtist(id, name)) },
            navigateToArtworkDetail = { navController.navigate(Screen.ArtworkDetail(it)) },
            onRetryLoadingArtist = viewModel::getArtist,
            navigateBack = navController::navigateUp
        )
    }
}
