package com.acm431.huzuratlasi.ui.theme

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination

sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    object Home : BottomNavItem("home", Icons.Default.Home, "Ana Sayfa")
    object Medicine : BottomNavItem("medicine", Icons.Default.Medication, "İlaçlar")
    object Emergency : BottomNavItem("emergency", Icons.Default.Emergency, "Acil")
    object News : BottomNavItem("news", Icons.Default.Article, "Haberler")
    object Profile : BottomNavItem("profile", Icons.Default.Person, "Profil")
}

@Composable
fun BottomNavigationBar(navController: NavController, currentRoute: String?) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Medicine,
        BottomNavItem.Emergency,
        BottomNavItem.News,
        BottomNavItem.Profile
    )

    NavigationBar(
        modifier = Modifier.navigationBarsPadding(),
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp
    ) {
        items.forEach { item ->
            val selected = currentRoute == item.route
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        modifier = Modifier.size(if (selected) 26.dp else 24.dp)
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        style = MaterialTheme.typography.labelSmall
                    )
                },
                selected = selected,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}

private data class NavTransition(
    val enterTransition: @Composable AnimatedContentTransitionScope<*>.() -> EnterTransition,
    val exitTransition: @Composable AnimatedContentTransitionScope<*>.() -> ExitTransition,
    val popEnterTransition: @Composable AnimatedContentTransitionScope<*>.() -> EnterTransition,
    val popExitTransition: @Composable AnimatedContentTransitionScope<*>.() -> ExitTransition
)


