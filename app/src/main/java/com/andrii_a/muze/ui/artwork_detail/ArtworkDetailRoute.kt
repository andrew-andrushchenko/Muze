package com.andrii_a.muze.ui.artwork_detail

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.andrii_a.muze.ui.navigation.Screen
import com.andrii_a.muze.ui.util.collectAsOneTimeEvents
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.artworkDetailRoute(navController: NavController) {
    composable<Screen.ArtworkDetail> {
        val viewModel: ArtworkDetailViewModel = koinViewModel()

        val state by viewModel.state.collectAsStateWithLifecycle()

        val shouldUseDarkIcons = !isSystemInDarkTheme()
        val view = LocalView.current

        DisposableEffect(key1 = state) {
            when {
                state.isLoading || state.error != null -> Unit
                else -> {
                    val window = (view.context as Activity).window
                    WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars =
                        false
                }
            }

            onDispose {
                when {
                    state.isLoading || state.error != null -> Unit
                    else -> {
                        val window = (view.context as Activity).window
                        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars =
                            shouldUseDarkIcons
                    }
                }
            }
        }

        viewModel.navigationEventsFlow.collectAsOneTimeEvents { event ->
            when (event) {
                ArtworkDetailNavigationEvent.NavigateBack -> navController.navigateUp()
            }
        }

        ArtworkDetailScreen(
            state = state,
            onEvent = viewModel::onEvent
        )
    }
}
