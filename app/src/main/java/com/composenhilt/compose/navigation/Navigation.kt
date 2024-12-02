package com.composenhilt.compose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.composenhilt.compose.launcher_screen.LauncherScreen
import com.composenhilt.compose.rocket_details_screen.RocketDetailsScreen
import com.composenhilt.compose.rocket_details_screen.ROCKET_ID_KEY
import com.composenhilt.compose.rockets_screen.RocketsScreen
import com.composenhilt.utils.screen_routes.Screens
import com.composenhilt.utils.screen_routes.Screens.ROCKET_DETAILS_SCREEN

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screens.LAUNCHER_SCREEN
    ) {
        composable(Screens.LAUNCHER_SCREEN) {
            LauncherScreen(navController = navController)
        }
        composable(Screens.ROCKETS_SCREEN) {
            RocketsScreen(navController)
        }
        composable("${ROCKET_DETAILS_SCREEN}/{$ROCKET_ID_KEY}", arguments = listOf(
            navArgument(ROCKET_ID_KEY) {
                type = NavType.StringType
                defaultValue = null
                nullable = true
            })) {
            RocketDetailsScreen(rocketId = it.arguments?.getString(ROCKET_ID_KEY, null)!!)
        }
    }
}
