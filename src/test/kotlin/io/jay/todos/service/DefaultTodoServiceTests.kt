package io.jay.todos.service

import io.jay.todos.controller.dto.NewTodoRequest
import io.jay.todos.controller.dto.UpdateTodoContentRequest
import io.jay.todos.model.Todo
import io.jay.todos.repository.TodoRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.nullValue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

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
    inner class UpdateContent {
        @Test
        fun `should call repository to find by id`() {
            every { mockTodoRepository.findById(1) } returns Todo(1, "Learn Kotlin", false)
            every { mockTodoRepository.save(any()) } returns Todo(1, "Learn Spring Boot", false)


            todoService.updateContent(1, UpdateTodoContentRequest("Learn Spring Boot"))


            verify { mockTodoRepository.findById(1) }
        }

        @Test
        fun `should call repository to save updated todo`() {
            every { mockTodoRepository.findById(1) } returns Todo(1, "Learn Kotlin", false)
            every { mockTodoRepository.save(any()) } returns Todo(1, "Learn Spring Boot", false)


            todoService.updateContent(1, UpdateTodoContentRequest("Learn Spring Boot"))


            verify { mockTodoRepository.save(Todo(1, "Learn Spring Boot", false)) }
        }

        @Test
        fun `should return updated todo`() {
            every { mockTodoRepository.findById(1) } returns Todo(1, "Learn Kotlin", false)
            every { mockTodoRepository.save(any()) } returns Todo(1, "Learn Spring Boot", false)


            val actual = todoService.updateContent(1, UpdateTodoContentRequest("Learn Spring Boot"))


            assertThat(actual!!.id, equalTo(1))
            assertThat(actual.description, equalTo("Learn Spring Boot"))
            assertThat(actual.finished, equalTo(false))
        }

        @Test
        fun `should preserve finished status when updating content`() {
            every { mockTodoRepository.findById(1) } returns Todo(1, "Learn Kotlin", true)
            every { mockTodoRepository.save(any()) } returns Todo(1, "Learn Spring Boot", true)


            val actual = todoService.updateContent(1, UpdateTodoContentRequest("Learn Spring Boot"))


            assertThat(actual!!.finished, equalTo(true))
        }

        @Test
        fun `should return null when todo not found`() {
            every { mockTodoRepository.findById(999) } returns null


            val actual = todoService.updateContent(999, UpdateTodoContentRequest("Learn Spring Boot"))


            assertThat(actual, nullValue())
        }

        @Test
        fun `should not call save when todo not found`() {
            every { mockTodoRepository.findById(999) } returns null


            todoService.updateContent(999, UpdateTodoContentRequest("Learn Spring Boot"))


            verify(exactly = 0) { mockTodoRepository.save(any()) }
        }
    }
}