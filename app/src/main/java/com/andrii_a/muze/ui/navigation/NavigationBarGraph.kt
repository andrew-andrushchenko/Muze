package com.andrii_a.muze.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigation
import com.andrii_a.muze.ui.artists.artistsRoute
import com.andrii_a.muze.ui.artworks.artworksRoute
import com.andrii_a.muze.ui.search.searchRoute
import kotlinx.serialization.Serializable

@Serializable
object NavigationBarGraph

fun NavGraphBuilder.navigationBarGraph(
    navHostController: NavHostController
) {
    navigation<NavigationBarGraph>(startDestination = Screen.Artists) {
        artistsRoute(navHostController)
        artworksRoute(navHostController)
        searchRoute(navHostController)
    }
}