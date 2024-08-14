package io.jay.todos.repository

import io.jay.todos.model.Todo

interface TodoRepository {
    fun findAll(): List<Todo>
    fun save(todo: Todo): Todo
}
