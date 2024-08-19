package com.example.taskmanagerapp

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(navController: NavController, viewModel: TaskViewModel = viewModel()) {
    val tasks by viewModel.tasks.collectAsState()
    val notificationDialogVisible by viewModel.notificationDialogVisible.collectAsState()
    val currentNotificationTask by viewModel.currentNotificationTask.collectAsState()


    if (notificationDialogVisible) {
        currentNotificationTask?.let { task->
            TaskNotificationDialog(task = task, onDismiss = { viewModel.dismissNotificationDialog() })
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("detail") }) {
                Icon(Icons.Filled.Add, contentDescription = "Add Task")
            }
        }
    )
    {

        LazyColumn {
            items(tasks) { task ->
                TaskItem(task = task, onTaskClick = { navController.navigate("detail/${task.id}") })
            }
        }
    }
}

@Composable
fun TaskItem(task: Task, onTaskClick: () -> Unit) {
    ListItem(
        modifier = Modifier.clickable(onClick = onTaskClick),
        headlineContent = { Text(task.title) },
        supportingContent = { CountdownTimer(task.remainingTime) },
        trailingContent = { Checkbox(
                checked = task.isCompleted,
                onCheckedChange = { isChecked ->
                    task.isCompleted = isChecked
                }
            )
        }
    )
}

@Composable
fun TaskNotificationDialog(task:Task, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Task Completed") },
        text = { Text("The task '${task.title}' has completed its countdown.") },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("OK")
            }
        }
    )

}


