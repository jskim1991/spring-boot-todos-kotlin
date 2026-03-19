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
        val entity = todoJpaRepository.findById(id).orElse(null)
        return if (entity != null) {
            Todo(entity.id!!, entity.description, entity.finished)
        } else {
            null
        }
    }

    override fun update(todo: Todo): Todo {
        val updated = todoJpaRepository.save(TodoEntity(todo.id, todo.description, todo.finished))
        return Todo(updated.id!!, updated.description, updated.finished)
    }
}