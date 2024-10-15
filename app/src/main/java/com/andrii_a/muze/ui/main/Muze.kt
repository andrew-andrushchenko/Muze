package com.andrii_a.muze.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import com.andrii_a.muze.R
import com.andrii_a.muze.ui.navigation.MainNavigationHost
import com.andrii_a.muze.ui.navigation.NavigationScreen
import com.andrii_a.muze.ui.theme.MuzeTheme
import com.andrii_a.muze.ui.util.NavigationScreenClassNames
import com.andrii_a.muze.ui.util.className
import com.andrii_a.muze.ui.util.currentScreenClassName

@Composable
fun Muze() {
    MuzeTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val navController = rememberNavController()

            Box(modifier = Modifier.imePadding()) {
                MainNavigationHost(navHostController = navController)

                if (navController.currentScreenClassName in NavigationScreenClassNames) {
                    NavigationBar(
                        modifier = Modifier.align(Alignment.BottomCenter)
                    ) {
                        NavigationScreen.entries.forEach { screen ->
                            NavigationBarItem(
                                icon = {
                                    Icon(
                                        imageVector = if (navController.currentScreenClassName == screen.className)
                                            screen.iconSelected
                                        else
                                            screen.iconUnselected,
                                        contentDescription = stringResource(id = R.string.navigation_icon)
                                    )
                                },
                                label = { Text(text = stringResource(id = screen.titleRes)) },
                                selected = navController.currentScreenClassName == screen.className,
                                onClick = {
                                    navController.navigate(screen.screen) {
                                        launchSingleTop = true
                                        restoreState = true
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}