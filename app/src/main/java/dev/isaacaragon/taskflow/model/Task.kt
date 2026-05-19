package dev.isaacaragon.taskflow.model

data class Task(
    val id: Int,
    val title: String,
    val completed: Boolean = false
)
