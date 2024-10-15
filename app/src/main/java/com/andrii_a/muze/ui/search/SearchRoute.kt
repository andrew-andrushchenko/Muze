package com.andrii_a.muze.ui.search

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.andrii_a.muze.ui.artist_detail.navigateToArtistDetail
import com.andrii_a.muze.ui.artwork_detail.navigateToArtworkDetail
import com.andrii_a.muze.ui.navigation.NavigationScreen

fun NavGraphBuilder.searchRoute(
    navController: NavController
) {
    composable(route = NavigationScreen.Search.route) {
        val view = LocalView.current
        val shouldUseDarkIcons = !isSystemInDarkTheme()

        LaunchedEffect(key1 = true) {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = shouldUseDarkIcons
        }

        val viewModel: SearchViewModel = hiltViewModel()

        SearchScreen(
            query = viewModel.query,
            artists = viewModel.artists,
            artworks = viewModel.artworks,
            onQueryChanged = viewModel::onQueryChanged,
            navigateToArtistDetail = navController::navigateToArtistDetail,
            navigateToArtworkDetail = navController::navigateToArtworkDetail
        )
    }
}