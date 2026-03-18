package io.jay.todos.repository

import io.jay.todos.entity.TodoEntity
import io.jay.todos.model.Todo
import org.springframework.stereotype.Repository

@Repository
class DefaultTodoRepository(private val todoJpaRepository: TodoJpaRepository) : TodoRepository {
    override fun findAll(): List<Todo> {
        return todoJpaRepository.findAll()
            .map { todoEntity ->
                Todo(todoEntity.id!!, todoEntity.description, todoEntity.finished)
            }
    }

    override fun save(todo: Todo): Todo {
        val created = todoJpaRepository.save(TodoEntity(todo.id, todo.description, todo.finished))
        return Todo(created.id!!, created.description, created.finished)
    }

    override fun updateDescription(id: Int, description: String): Todo? {
        val entity = todoJpaRepository.findById(id).orElse(null) ?: return null
        val updatedEntity = todoJpaRepository.save(TodoEntity(entity.id, description, entity.finished))
        return Todo(updatedEntity.id!!, updatedEntity.description, updatedEntity.finished)
    }
}