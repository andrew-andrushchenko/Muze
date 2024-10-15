package com.andrii_a.muze.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.andrii_a.muze.ui.artist_detail.artistDetailRoute
import com.andrii_a.muze.ui.artwork_detail.artworkDetailRoute
import com.andrii_a.muze.ui.artworks_by_artist.artworksByArtistRoute

@Composable
fun MainNavigationHost(
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navHostController,
        startDestination = NavigationBarGraph,
        modifier = modifier
    ) {
        navigationBarGraph(navHostController)

        artistDetailRoute(navHostController)
        artworkDetailRoute(navHostController)
        artworksByArtistRoute(navHostController)
    }
}
