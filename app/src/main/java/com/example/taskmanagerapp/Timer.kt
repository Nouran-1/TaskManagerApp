package com.example.taskmanagerapp

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay

@Composable
fun CountdownTimer(duration: Long) {
    var remainingTime by remember { mutableLongStateOf(duration) }

    LaunchedEffect(remainingTime) {
        while (remainingTime > 0) {
            delay(1000L) // 1 minute delay
            remainingTime -= 1
        }
    }

    val seconds = remainingTime
    Text(text = "$seconds second${if (seconds > 1) "s" else ""}")
}
