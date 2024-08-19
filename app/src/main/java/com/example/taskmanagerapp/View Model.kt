package com.example.taskmanagerapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class TaskViewModel(application: Application , val stateHandle: SavedStateHandle) : AndroidViewModel(application) {
    val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks
    val dp = ListDatabase.getDatabase(application)
    private val _notificationDialogVisible = MutableStateFlow(false)
    val notificationDialogVisible: StateFlow<Boolean> = _notificationDialogVisible
    private val _currentNotificationTask = MutableStateFlow<Task?>(null)
    val currentNotificationTask: StateFlow<Task?> = _currentNotificationTask

    init {
        viewModelScope.launch {
            val listdao = dp.listDao().getData()
            _tasks.value = listdao
        }

        stateHandle.get<List<Task>>("tasks")?.let {
            _tasks.value = it
        }

    }

    fun addTask(task: Task) {
        _tasks.value += task
        saveTasks()
    }

    fun updateTask(task: Task) {
        _tasks.value = _tasks.value.map { if (it.id == task.id) task else it }
        saveTasks()
    }

    fun deleteTask(taskId: Int) {
        _tasks.value = _tasks.value.filter { it.id != taskId }
        saveTasks()
    }

    fun saveTasks() {
        stateHandle.set("tasks", _tasks.value)
        viewModelScope.launch {
            dp.listDao().addDataList(_tasks.value)
        }
    }

    fun startTimer(task: Task) {
        viewModelScope.launch {
            while (task.remainingTime > 0) {
                task.remainingTime -= 1
                updateTask(task)
                kotlinx.coroutines.delay(60000) //
            }
        }
    }

     fun showNotificationDialog (task: Task) {
        _currentNotificationTask.value = task
        _notificationDialogVisible.value = true
    }

    fun dismissNotificationDialog() {
        _notificationDialogVisible.value = false
        _currentNotificationTask.value = null
    }
}
