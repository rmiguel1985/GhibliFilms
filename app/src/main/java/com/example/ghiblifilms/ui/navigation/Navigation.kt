package com.example.ghiblifilms.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.ghiblifilms.features.film_detail.ui.DetailScreen
import com.example.ghiblifilms.features.films.ui.view.MainScreen
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun Navigation(navController: NavHostController =  rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Home
    ) {
        composable<Home> {
            MainScreen { filmModel ->
                navController.navigate(Detail(filmModel.id))

            }
        }
        composable<Detail> { backStackEntry ->
            val movieId = backStackEntry.toRoute<Detail>().movieId
            DetailScreen(
                onBack = { navController.popBackStack() },
                viewModel = koinViewModel(parameters = { parametersOf(movieId) })
            )
        }
    }
}
