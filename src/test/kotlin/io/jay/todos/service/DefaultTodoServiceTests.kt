package io.jay.todos.service

import io.jay.todos.controller.dto.NewTodoRequest
import io.jay.todos.model.Todo
import io.jay.todos.repository.TodoRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.web.server.ResponseStatusException

class DefaultTodoServiceTests {

    private lateinit var todoService: TodoService
    private lateinit var mockTodoRepository: TodoRepository

    @BeforeEach
    fun setUp() {
        mockTodoRepository = mockk()
        todoService = DefaultTodoService(mockTodoRepository)
    }

    @Nested
    inner class GetAll {
        @Test
        fun `should call repository to find all`() {
            every { mockTodoRepository.findAll() } returns emptyList()


            todoService.getAll()


            verify { mockTodoRepository.findAll() }
        }

        @Test
        fun `should return list of todo`() {
            every { mockTodoRepository.findAll() } returns listOf(
                Todo(1, "Learn Kotlin", true)
            )


            val actual = todoService.getAll()


            assertThat(actual.size, equalTo(1))
            val todo = actual.get(0)
            assertThat(todo.id, equalTo(1))
            assertThat(todo.description, equalTo("Learn Kotlin"))
            assertThat(todo.finished, equalTo(true))
        }
    }

    @Nested
    inner class Create {
        @Test
        fun `should call repository to save`() {
            every { mockTodoRepository.save(Todo("Learn Kotlin")) } returns Todo(1, "Learn Kotlin", false)


            todoService.create(NewTodoRequest("Learn Kotlin"))


            verify { mockTodoRepository.save(any()) }
        }

        @Test
        fun `should return created todo`() {
            every { mockTodoRepository.save(any()) } returns Todo(1, "Learn Kotlin", false)


            val actual = todoService.create(NewTodoRequest("Learn Kotlin"))


            assertThat(actual.id, equalTo(1))
            assertThat(actual.description, equalTo("Learn Kotlin"))
            assertThat(actual.finished, equalTo(false))
        }
    }

    @Nested
    inner class UpdateDescription {
        @Test
        fun `should call repository to find and save`() {
            every { mockTodoRepository.findById(1) } returns Todo(1, "Learn Kotlin", false)
            every { mockTodoRepository.save(Todo(1, "Updated description", false)) } returns Todo(1, "Updated description", false)


            todoService.updateDescription(1, "Updated description")


            verify { mockTodoRepository.findById(1) }
            verify { mockTodoRepository.save(Todo(1, "Updated description", false)) }
        }

        @Test
        fun `should return updated todo`() {
            every { mockTodoRepository.findById(1) } returns Todo(1, "Learn Kotlin", false)
            every { mockTodoRepository.save(any()) } returns Todo(1, "Updated description", false)


            val actual = todoService.updateDescription(1, "Updated description")


            assertThat(actual.id, equalTo(1))
            assertThat(actual.description, equalTo("Updated description"))
            assertThat(actual.finished, equalTo(false))
        }

        @Test
        fun `should throw ResponseStatusException when todo not found`() {
            every { mockTodoRepository.findById(1) } returns null


            assertThrows<ResponseStatusException> {
                todoService.updateDescription(1, "Updated description")
            }
        }
    }
}