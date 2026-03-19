package io.jay.todos.service

import io.jay.todos.controller.dto.NewTodoRequest
import io.jay.todos.controller.dto.UpdateTodoContentRequest
import io.jay.todos.model.Todo

interface TodoService {

    fun getAll(): List<Todo>
    fun create(newTodoRequest: NewTodoRequest): Todo
    fun updateContent(id: Int, updateTodoContentRequest: UpdateTodoContentRequest): Todo?
}