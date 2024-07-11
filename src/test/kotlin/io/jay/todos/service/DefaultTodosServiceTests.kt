package io.jay.todos.service

import io.jay.todos.model.Todo
import io.jay.todos.repository.TodosRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DefaultTodosServiceTests {

    private lateinit var todosService: TodosService
    private lateinit var mockTodosRepository: TodosRepository

    @BeforeEach
    fun setUp() {
        mockTodosRepository = mockk()
        todosService = DefaultTodosService(mockTodosRepository)
    }

    @Test
    fun getAll_shouldCallRepository() {
        every { mockTodosRepository.findAll() } returns emptyList()


        todosService.getAll()


        verify { mockTodosRepository.findAll() }
    }

    @Test
    fun getAll_shouldReturnListOfTodos() {
        every { mockTodosRepository.findAll() } returns listOf(
            Todo(1, "Learn Kotlin", true)
        )


        val actual = todosService.getAll()


        assertThat(actual.size, equalTo(1))
        val todo = actual.get(0)
        assertThat(todo.id, equalTo(1))
        assertThat(todo.description, equalTo("Learn Kotlin"))
        assertThat(todo.finished, equalTo(true))
    }
}