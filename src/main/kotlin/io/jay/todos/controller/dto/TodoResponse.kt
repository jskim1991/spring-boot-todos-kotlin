package io.jay.todos.controller.dto

import com.fasterxml.jackson.annotation.JsonInclude
import io.jay.todos.model.Priority

@JsonInclude(JsonInclude.Include.NON_NULL)
data class TodoResponse(val id: Int, val description: String, val finished: Boolean, val priority: Priority? = null)
