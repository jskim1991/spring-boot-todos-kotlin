package io.jay.todos.service

import io.jay.todos.controller.dto.NewTodoRequest
import io.jay.todos.controller.dto.UpdateTodoRequest
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

    override fun update(id: Int, updateTodoRequest: UpdateTodoRequest): Todo {
        val existing = todoRepository.findById(id)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)

        return todoRepository.save(existing.copy(description = updateTodoRequest.description, finished = updateTodoRequest.finished))
    }
}
