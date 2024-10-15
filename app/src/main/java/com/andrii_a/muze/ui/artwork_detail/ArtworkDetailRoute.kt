package com.andrii_a.muze.ui.artwork_detail

import android.app.Activity
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

fun NavGraphBuilder.artworkDetailRoute(
    navController: NavController
) {
    composable<Screen.ArtistDetail> { navBackStackEntry ->
        val viewModel: ArtworkDetailViewModel = hiltViewModel()
        val loadResult = viewModel.loadResult.collectAsStateWithLifecycle()

        val view = LocalView.current
        //val systemBarsColor = Color.Transparent
        //val areIconsDark = !isSystemInDarkTheme()

        LaunchedEffect(key1 = loadResult.value) {
            when (loadResult.value) {
                ArtworkLoadResult.Empty,
                ArtworkLoadResult.Error,
                ArtworkLoadResult.Loading -> {
                    val window = (view.context as Activity).window
                    WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
                }

                is ArtworkLoadResult.Success -> {
                    val window = (view.context as Activity).window
                    WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
                }
            }
        }

        val artworkId = navBackStackEntry.toRoute<Screen.ArtworkDetail>().artworkId

        ArtworkDetailScreen(
            artworkId = artworkId,
            loadResult = loadResult.value,
            onRetryLoadingArtwork = viewModel::getArtwork,
            navigateBack = navController::navigateUp
        )
    }
}
