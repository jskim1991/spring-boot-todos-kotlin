package io.jay.todos.service

import io.jay.todos.controller.dto.NewTodoRequest
import io.jay.todos.model.Todo
import io.jay.todos.repository.TodoRepository
import org.springframework.stereotype.Service

@Service
class DefaultTodoService(private val todoRepository: TodoRepository) : TodoService {
    override fun getAll(): List<Todo> {
        return todoRepository.findAll()
    }

    override fun create(newTodoRequest: NewTodoRequest): Todo {
        return todoRepository.save(Todo(newTodoRequest.description))
    }
}
