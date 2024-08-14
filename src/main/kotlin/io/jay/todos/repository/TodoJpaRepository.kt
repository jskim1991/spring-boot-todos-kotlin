package io.jay.todos.repository

import io.jay.todos.entity.TodoEntity
import org.springframework.data.jpa.repository.JpaRepository

interface TodoJpaRepository : JpaRepository<TodoEntity, Int> {
}