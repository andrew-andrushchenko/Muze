package com.andrii_a.muze.ui.artworks_by_artist

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.andrii_a.muze.ui.navigation.Screen
import com.andrii_a.muze.ui.util.collectAsOneTimeEvents

fun NavGraphBuilder.artworksByArtistRoute(navController: NavController) {
    composable<Screen.ArtworksByArtist> {
        val view = LocalView.current
        val shouldUseDarkIcons = !isSystemInDarkTheme()

        LaunchedEffect(key1 = true) {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars =
                shouldUseDarkIcons
        }

        val viewModel: ArtworksByArtistViewModel = hiltViewModel()

        val state by viewModel.state.collectAsStateWithLifecycle()

        viewModel.navigationEventsFlow.collectAsOneTimeEvents { event ->
            when (event) {
                is ArtworksByArtistNavigationEvent.NavigateBack -> {
                    navController.navigateUp()
                }

                is ArtworksByArtistNavigationEvent.NavigateToArtworkDetail -> {
                    navController.navigate(Screen.ArtworkDetail(event.artworkId))
                }
            }
        }

        ArtworksByArtistScreen(
            state = state,
            onEvent = viewModel::onEvent
        )
    }
}
