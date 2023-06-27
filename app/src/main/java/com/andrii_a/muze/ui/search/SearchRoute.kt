package com.andrii_a.muze.ui.search

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.andrii_a.muze.ui.artist_detail.navigateToArtistDetail
import com.andrii_a.muze.ui.artwork_detail.navigateToArtworkDetail
import com.andrii_a.muze.ui.navigation.NavigationScreen
import com.google.accompanist.systemuicontroller.SystemUiController

fun NavGraphBuilder.searchRoute(
    navController: NavController,
    systemUiController: SystemUiController
) {
    composable(route = NavigationScreen.Search.route) {
        val systemBarsColor = Color.Transparent
        val areIconsDark = !isSystemInDarkTheme()

        LaunchedEffect(key1 = true) {
            systemUiController.setSystemBarsColor(
                color = systemBarsColor,
                darkIcons = areIconsDark
            )
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