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
        val entity = todoJpaRepository.findById(id)
        return if (entity.isPresent) {
            val todoEntity = entity.get()
            Todo(todoEntity.id!!, todoEntity.description, todoEntity.finished)
        } else {
            null
        }
    }

    override fun update(id: Int, description: String): Todo? {
        val entity = todoJpaRepository.findById(id)
        return if (entity.isPresent) {
            val todoEntity = entity.get()
            todoEntity.description = description
            val updated = todoJpaRepository.save(todoEntity)
            Todo(updated.id!!, updated.description, updated.finished)
        } else {
            null
        }
    }
}