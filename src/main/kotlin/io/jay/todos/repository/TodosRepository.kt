package io.jay.todos.repository

import io.jay.todos.model.Todo

interface TodosRepository {
    fun findAll(): List<Todo>
}
