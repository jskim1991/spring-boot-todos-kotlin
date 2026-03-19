package io.jay.todos.controller.dto

import io.jay.todos.model.Priority

data class NewTodoRequest(val description: String, val priority: Priority? = null)
