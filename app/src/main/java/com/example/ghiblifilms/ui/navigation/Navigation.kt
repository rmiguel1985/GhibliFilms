package com.example.ghiblifilms.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ghiblifilms.features.film_detail.ui.DetailScreen
import com.example.ghiblifilms.features.films.ui.view.MainScreen

@Composable
fun Navigation(navController: NavHostController =  rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = NavItem.Main.route
    ) {
        composable(NavItem.Main.route) {
            MainScreen { filmModel ->
                navController.navigate(NavItem.Detail.createNavRoute(filmModel.id))

            }
        }
        composable(
            route = NavItem.Detail.route,
        ) {
            DetailScreen(
                onUpClick = { navController.popBackStack() }
            )
        }
    }
}
