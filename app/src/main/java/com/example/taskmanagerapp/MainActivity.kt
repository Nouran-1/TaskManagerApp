package com.example.taskmanagerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.taskmanagerapp.ui.theme.TaskManagerAppTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskManagerAppTheme {
                val navController = rememberNavController()
                val taskViewModel: TaskViewModel = viewModel()
                TaskManagerApp(navController, taskViewModel)




            }

        }
    }
}

