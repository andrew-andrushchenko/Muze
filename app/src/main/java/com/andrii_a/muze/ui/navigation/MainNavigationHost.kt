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
import com.google.accompanist.systemuicontroller.SystemUiController

@Composable
fun MainNavigationHost(
    navHostController: NavHostController,
    systemUiController: SystemUiController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navHostController,
        startDestination = NAVIGATION_BAR_GRAPH_ROUTE,
        modifier = modifier
    ) {
        navigationBarGraph(navHostController, systemUiController)

        artistDetailRoute(navHostController, systemUiController)
        artworkDetailRoute(navHostController, systemUiController)
    }
}

fun NavGraphBuilder.navigationBarGraph(
    navHostController: NavHostController,
    systemUiController: SystemUiController
) {
    navigation(
        route = NAVIGATION_BAR_GRAPH_ROUTE,
        startDestination = NavigationScreen.Artists.route
    ) {
        artistsRoute(navHostController, systemUiController)
        artworksRoute(navHostController, systemUiController)
    }
}

private const val NAVIGATION_BAR_GRAPH_ROUTE = "muze_navigation_bar_graph_route"