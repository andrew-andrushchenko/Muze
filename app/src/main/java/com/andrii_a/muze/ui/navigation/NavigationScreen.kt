package com.andrii_a.muze.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.andrii_a.muze.R

enum class NavigationScreen(
    val route: String,
    @StringRes val titleRes: Int,
    val iconUnselected: ImageVector,
    val iconSelected: ImageVector
) {
    Artists(
        route = "nav_artists",
        titleRes = R.string.artists,
        iconSelected = Icons.Filled.Groups,
        iconUnselected = Icons.Outlined.Groups
    ),
    Artworks(
        route = "nav_artworks",
        titleRes = R.string.artworks,
        iconSelected = Icons.Filled.Image,
        iconUnselected = Icons.Outlined.Image
    ),
    Search(
        route = "nav_search",
        titleRes = R.string.search,
        iconSelected = Icons.Filled.Search,
        iconUnselected = Icons.Outlined.Search
    )
}

val NavigationScreenRoutes: List<String> by lazy {
    NavigationScreen.entries.map { it.route }
}