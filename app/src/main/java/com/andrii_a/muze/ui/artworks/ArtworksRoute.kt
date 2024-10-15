package com.andrii_a.muze.ui.artworks

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.andrii_a.muze.ui.navigation.Screen

fun NavGraphBuilder.artworksRoute(
    navController: NavController
) {
    composable<Screen.Artworks> {
        val view = LocalView.current
        val shouldUseDarkIcons = !isSystemInDarkTheme()

        LaunchedEffect(key1 = true) {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = shouldUseDarkIcons
        }

        val viewModel: ArtworksViewModel = hiltViewModel()

        ArtworksScreen(
            artworksFlow = viewModel.artworks,
            onArtworkSelected = { navController.navigate(Screen.ArtworkDetail(it)) }
        )
    }
}
