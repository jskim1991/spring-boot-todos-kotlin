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

    override fun findById(id: Int): Todo? {
        return todoJpaRepository.findById(id)
            .map { todoEntity ->
                Todo(todoEntity.id!!, todoEntity.description, todoEntity.finished)
            }
            .orElse(null)
    }

    override fun update(id: Int, description: String): Todo? {
        val existingTodo = todoJpaRepository.findById(id)
        if (existingTodo.isEmpty) {
            return null
        }
        val entity = existingTodo.get()
        val updatedEntity = todoJpaRepository.save(TodoEntity(entity.id, description, entity.finished))
        return Todo(updatedEntity.id!!, updatedEntity.description, updatedEntity.finished)
    }
}