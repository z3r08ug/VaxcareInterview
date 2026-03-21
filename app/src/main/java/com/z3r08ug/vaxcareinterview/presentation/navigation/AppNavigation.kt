package com.z3r08ug.vaxcareinterview.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.z3r08ug.vaxcareinterview.presentation.greeting.GreetingScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "greeting"
    ) {
        composable("greeting") {
            GreetingScreen()
        }
    }
}
