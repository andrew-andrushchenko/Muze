package com.andrii_a.muze.ui.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.andrii_a.muze.ui.navigation.NavigationScreen

typealias RouteClassName = String

val NavigationScreenClassNames: List<RouteClassName?> by lazy {
    NavigationScreen.entries.map { it.screen::class.simpleName }
}

val NavController.currentScreenClassName: RouteClassName?
    @Composable
    get() {
        val navBackStackEntry by this.currentBackStackEntryAsState()
        return navBackStackEntry?.destination?.route
            ?.substringBefore("?")
            ?.substringBefore("/")
            ?.substringAfterLast(".")
    }

val NavigationScreen.className: RouteClassName?
    get() = this.screen::class.simpleName