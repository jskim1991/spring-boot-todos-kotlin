package io.jay.todos.service

import io.jay.todos.model.Todo
import io.jay.todos.repository.TodosRepository
import org.springframework.stereotype.Service

@Service
class DefaultTodosService(private val todosRepository: TodosRepository) : TodosService {
    override fun getAll(): List<Todo> {
        return todosRepository.findAll()
    }
}
