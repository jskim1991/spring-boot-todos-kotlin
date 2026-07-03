package io.jay.todos.controller

import com.fasterxml.jackson.databind.ObjectMapper
import io.jay.todos.controller.dto.NewTodoRequest
import io.jay.todos.controller.dto.TodoResponse
import io.jay.todos.model.Todo
import io.jay.todos.service.TodoService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders

class TodosControllerTests {

    private lateinit var mockMvc: MockMvc
    private lateinit var mockTodoService: TodoService
    private val objectMapper = ObjectMapper()

    @BeforeEach
    fun setUp() {
        mockTodoService = mockk()
        mockMvc = MockMvcBuilders.standaloneSetup(TodosController(mockTodoService)).build()
    }

    @Nested
    inner class GetAll {
        @Test
        fun `should return 200 OK`() {
            every { mockTodoService.getAll() } returns emptyList()


            mockMvc.perform(get("/api/todos"))
                .andExpect(status().isOk)
        }

        @Test
        fun `should call todos service`() {
            every { mockTodoService.getAll() } returns emptyList()


            mockMvc.perform(get("/api/todos"))


            verify { mockTodoService.getAll() }
        }

        @Test
        fun `should return list of todos`() {
            every { mockTodoService.getAll() } returns listOf(
                Todo(1, "Learn Kotlin", true)
            )

            val expectedJson = """
                [
                    {
                        "id": 1,
                        "description": "Learn Kotlin",
                        "finished": true
                    }
                ]
            """.trimIndent()


            mockMvc.perform(get("/api/todos"))
                .andExpect(content().json(expectedJson, true))
        }
    }

    @Nested
    inner class GetById {
        @Test
        fun `should return 200 OK`() {
            every { mockTodoService.getById(1) } returns Todo(1, "Learn Kotlin", true)


            mockMvc.perform(get("/api/todos/1"))
                .andExpect(status().isOk)
        }

        @Test
        fun `should call todos service`() {
            every { mockTodoService.getById(1) } returns Todo(1, "Learn Kotlin", true)


            mockMvc.perform(get("/api/todos/1"))


            verify { mockTodoService.getById(1) }
        }

        @Test
        fun `should return todo`() {
            every { mockTodoService.getById(1) } returns Todo(1, "Learn Kotlin", true)

            val expectedJson = """
                {
                    "id": 1,
                    "description": "Learn Kotlin",
                    "finished": true
                }
            """.trimIndent()


            mockMvc.perform(get("/api/todos/1"))
                .andExpect(content().json(expectedJson, true))
        }

        @Test
        fun `should return 404 when not found`() {
            every { mockTodoService.getById(1) } returns null


            mockMvc.perform(get("/api/todos/1"))
                .andExpect(status().isNotFound)
        }
    }

    @Nested
    inner class CreateTodo {
        val newTodoRequest = NewTodoRequest("Learn Kotlin")
        val requestBody = objectMapper.writeValueAsString(newTodoRequest)

        @Test
        fun `should return 201`() {
            every { mockTodoService.create(any()) } returns Todo(1, "Learn Kotlin", false)


            mockMvc.perform(post("/api/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isCreated)
        }

        @Test
        fun `should call todoService`() {
            every { mockTodoService.create(newTodoRequest) } returns Todo(1, "Learn Kotlin", false)


            mockMvc.perform(post("/api/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))


            verify { mockTodoService.create(newTodoRequest) }
        }

        @Test
        fun `should return created todo`() {
            every { mockTodoService.create(any()) } returns Todo(1, "Learn Kotlin", false)


            val expectedJson = """
                {
                    "id": 1,
                    "description": "Learn Kotlin",
                    "finished": false
                }
            """.trimIndent()


            mockMvc.perform(post("/api/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(content().json(expectedJson, true))
        }
    }

    @Nested
    inner class FinishTodo {
        @Test
        fun `should return 200 OK`() {
            every { mockTodoService.finish(1) } returns Todo(1, "Learn Kotlin", true)


            mockMvc.perform(put("/api/todos/1/finish"))
                .andExpect(status().isOk)
        }

        @Test
        fun `should call todoService`() {
            every { mockTodoService.finish(1) } returns Todo(1, "Learn Kotlin", true)


            mockMvc.perform(put("/api/todos/1/finish"))


            verify { mockTodoService.finish(1) }
        }

        @Test
        fun `should return finished todo`() {
            every { mockTodoService.finish(1) } returns Todo(1, "Learn Kotlin", true)

            val expectedJson = """
                {
                    "id": 1,
                    "description": "Learn Kotlin",
                    "finished": true
                }
            """.trimIndent()


            mockMvc.perform(put("/api/todos/1/finish"))
                .andExpect(content().json(expectedJson, true))
        }

        @Test
        fun `should return 404 when not found`() {
            every { mockTodoService.finish(1) } returns null


            mockMvc.perform(put("/api/todos/1/finish"))
                .andExpect(status().isNotFound)
        }
    }

    @Nested
    inner class DeleteTodo {
        @Test
        fun `should return 204`() {
            every { mockTodoService.delete(1) } returns true


            mockMvc.perform(delete("/api/todos/1"))
                .andExpect(status().isNoContent)
        }

        @Test
        fun `should call todoService`() {
            every { mockTodoService.delete(1) } returns true


            mockMvc.perform(delete("/api/todos/1"))


            verify { mockTodoService.delete(1) }
        }

        @Test
        fun `should return 404 when not found`() {
            every { mockTodoService.delete(1) } returns false


            mockMvc.perform(delete("/api/todos/1"))
                .andExpect(status().isNotFound)
        }
    }

}