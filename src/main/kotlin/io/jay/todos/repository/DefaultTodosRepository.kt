package io.jay.todos.repository

import io.jay.todos.model.Todo
import org.springframework.stereotype.Repository

@Repository
class DefaultTodosRepository(private val todosJpaRepository: TodosJpaRepository) : TodosRepository {
    override fun findAll(): List<Todo> {
        return todosJpaRepository.findAll()
            .map { todoEntity ->
                Todo(todoEntity.id!!, todoEntity.description, todoEntity.finished)
            }
    }
}