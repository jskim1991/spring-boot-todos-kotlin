package io.jay.todos.repository

import io.jay.todos.entity.TodoEntity
import org.springframework.data.jpa.repository.JpaRepository

interface TodosJpaRepository : JpaRepository<TodoEntity, Int> {
}