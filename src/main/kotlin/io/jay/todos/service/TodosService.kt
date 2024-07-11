package io.jay.todos.service

import io.jay.todos.model.Todo

interface TodosService {

    fun getAll(): List<Todo>
}