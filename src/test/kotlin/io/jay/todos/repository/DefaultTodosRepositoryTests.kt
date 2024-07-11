package io.jay.todos.repository

import io.jay.todos.entity.TodoEntity
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DefaultTodosRepositoryTests {

    private lateinit var todosRepository: TodosRepository
    private lateinit var mockTodosJpaRepository: TodosJpaRepository

    @BeforeEach
    fun setUp() {
        mockTodosJpaRepository = mockk()
        todosRepository = DefaultTodosRepository(mockTodosJpaRepository)
    }

    @Test
    fun findAll_shouldCallJpaRepository() {
        every { mockTodosJpaRepository.findAll() } returns emptyList()


        todosRepository.findAll()


        verify { mockTodosJpaRepository.findAll() }
    }

    @Test
    fun findAll_shouldReturnListOfTodos() {
        every { mockTodosJpaRepository.findAll() } returns listOf(
            TodoEntity(1, "Learn Kotlin", true)
        )


        val actual = todosRepository.findAll()


        assertThat(actual.size, equalTo(1))
        val todo = actual.get(0)
        assertThat(todo.id, equalTo(1))
        assertThat(todo.description, equalTo("Learn Kotlin"))
        assertThat(todo.finished, equalTo(true))
    }
}