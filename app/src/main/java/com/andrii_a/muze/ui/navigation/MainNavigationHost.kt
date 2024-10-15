package com.andrii_a.muze.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.navigation
import com.andrii_a.muze.ui.artist_detail.artistDetailRoute
import com.andrii_a.muze.ui.artists.artistsRoute
import com.andrii_a.muze.ui.artwork_detail.artworkDetailRoute
import com.andrii_a.muze.ui.artworks.artworksRoute
import com.andrii_a.muze.ui.artworks_by_artist.artworksByArtistRoute
import com.andrii_a.muze.ui.search.searchRoute

@Composable
fun MainNavigationHost(
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navHostController,
        startDestination = NAVIGATION_BAR_GRAPH_ROUTE,
        modifier = modifier
    ) {
        navigationBarGraph(navHostController)

        artistDetailRoute(navHostController)
        artworkDetailRoute(navHostController)
        artworksByArtistRoute(navHostController)
    }
}

fun NavGraphBuilder.navigationBarGraph(
    navHostController: NavHostController
) {
    navigation(
        route = NAVIGATION_BAR_GRAPH_ROUTE,
        startDestination = NavigationScreen.Artists.route
    ) {
        artistsRoute(navHostController)
        artworksRoute(navHostController)
        searchRoute(navHostController)
    }
}

private const val NAVIGATION_BAR_GRAPH_ROUTE = "muze_navigation_bar_graph_route"