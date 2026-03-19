package io.jay.todos.controller.dto

data class UpdateTodoRequest(
    val description: String,
    val finished: Boolean
)
