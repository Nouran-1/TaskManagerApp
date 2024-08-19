package com.example.taskmanagerapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun TaskManagerApp(navController: NavHostController, taskViewModel: TaskViewModel) {
    NavHost(navController, startDestination = "main") {
        composable("main") {
            MainScreen(navController, taskViewModel)
        }
        composable("detail/{taskId}") { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId")?.toIntOrNull()
            DetailScreen(navController, taskViewModel, taskId)
        }
        composable("detail") {
            DetailScreen(navController, taskViewModel, null)
        }
    }
}
