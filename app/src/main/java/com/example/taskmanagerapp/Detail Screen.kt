package com.example.taskmanagerapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun DetailScreen(navController: NavController, viewModel: TaskViewModel = viewModel(), taskId: Int?) {
    val task = taskId?.let { id ->
        viewModel.tasks.collectAsState().value.find {true}
    } ?: Task(id = (0..1000).random(), title = "id", isCompleted = false, duration = 0, remainingTime = 0)

    var title by remember { mutableStateOf(task.title) }
    var duration by remember { mutableLongStateOf(task.duration) }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(value = title, onValueChange = { title = it }, label = { Text("Task Title") })
        TextField(
            value = duration.toString(),
            onValueChange = { duration = it.toLongOrNull() ?: 0 },
            label = { Text("Duration (seconds)") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            if (taskId == null) {
                viewModel.addTask(Task(id = 0, title = title, isCompleted = false, duration = duration, remainingTime = duration))
            } else {
                task.title = title
                task.duration = duration
                task.remainingTime = duration
                viewModel.run {
                    updateTask(task)
                    startTimer(task)
                }
            }
            navController.navigateUp()
        }) {
            Text("Save Task")
        }
    }
}
