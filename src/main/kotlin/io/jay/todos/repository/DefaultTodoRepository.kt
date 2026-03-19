package io.jay.todos.repository

import io.jay.todos.entity.TodoEntity
import io.jay.todos.model.Todo
import org.springframework.stereotype.Repository

@Repository
class DefaultTodoRepository(private val todoJpaRepository: TodoJpaRepository) : TodoRepository {
    override fun findAll(): List<Todo> {
        return todoJpaRepository.findAll()
            .map { todoEntity ->
                Todo(todoEntity.id!!, todoEntity.description, todoEntity.finished, todoEntity.label)
            }
    }

    override fun findById(id: Int): Todo? {
        return todoJpaRepository.findById(id)
            .map { todoEntity ->
                Todo(todoEntity.id!!, todoEntity.description, todoEntity.finished, todoEntity.label)
            }
            .orElse(null)
    }

    override fun save(todo: Todo): Todo {
        val created = todoJpaRepository.save(TodoEntity(todo.id, todo.description, todo.finished, todo.label))
        return Todo(created.id!!, created.description, created.finished, created.label)
    }
}