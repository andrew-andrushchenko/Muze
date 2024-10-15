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
    val screen: Screen,
    @StringRes val titleRes: Int,
    val iconUnselected: ImageVector,
    val iconSelected: ImageVector
) {
    Artists(
        screen = Screen.Artists,
        titleRes = R.string.artists,
        iconSelected = Icons.Filled.Groups,
        iconUnselected = Icons.Outlined.Groups
    ),
    Artworks(
        screen = Screen.Artworks,
        titleRes = R.string.artworks,
        iconSelected = Icons.Filled.Image,
        iconUnselected = Icons.Outlined.Image
    ),
    Search(
        screen = Screen.Search,
        titleRes = R.string.search,
        iconSelected = Icons.Filled.Search,
        iconUnselected = Icons.Outlined.Search
    )
}
