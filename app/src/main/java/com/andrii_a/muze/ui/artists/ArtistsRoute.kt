package com.andrii_a.muze.ui.artists

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

fun NavGraphBuilder.artistsRoute(
    navController: NavController
) {
    composable<Screen.Artists> {
        val view = LocalView.current
        val shouldUseDarkIcons = !isSystemInDarkTheme()

        LaunchedEffect(key1 = true) {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = shouldUseDarkIcons
        }

        val viewModel: ArtistsViewModel = hiltViewModel()

        ArtistsScreen(
            artistsFlow = viewModel.artists,
            navigateToArtistDetail = { navController.navigate(Screen.ArtistDetail(it)) }
        )
    }
}
