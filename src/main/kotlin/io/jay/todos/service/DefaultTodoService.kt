package io.jay.todos.service

import io.jay.todos.controller.dto.NewTodoRequest
import io.jay.todos.model.Todo
import io.jay.todos.repository.TodoRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class DefaultTodoService(private val todoRepository: TodoRepository) : TodoService {
    override fun getAll(): List<Todo> {
        return todoRepository.findAll()
    }

    override fun create(newTodoRequest: NewTodoRequest): Todo {
        return todoRepository.save(Todo(newTodoRequest.description))
    }

    override fun updateDescription(id: Int, description: String): Todo {
        val todo = todoRepository.findById(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
        return todoRepository.save(Todo(todo.id, description, todo.finished))
    }
}
