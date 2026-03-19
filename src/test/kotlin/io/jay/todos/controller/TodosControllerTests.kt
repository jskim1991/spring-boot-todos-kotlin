package io.jay.todos.controller

import com.fasterxml.jackson.databind.ObjectMapper
import io.jay.todos.controller.dto.NewTodoRequest
import io.jay.todos.controller.dto.TodoResponse
import io.jay.todos.controller.dto.UpdateTodoContentRequest
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
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
    inner class UpdateTodoContent {
        val updateTodoContentRequest = UpdateTodoContentRequest("Learn Spring Boot")
        val requestBody = objectMapper.writeValueAsString(updateTodoContentRequest)

        @Test
        fun `should return 200 OK when todo exists`() {
            every { mockTodoService.updateContent(1, any()) } returns Todo(1, "Learn Spring Boot", false)


            mockMvc.perform(patch("/api/todos/1/content")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk)
        }

        @Test
        fun `should call todoService with correct parameters`() {
            every { mockTodoService.updateContent(1, updateTodoContentRequest) } returns Todo(1, "Learn Spring Boot", false)


            mockMvc.perform(patch("/api/todos/1/content")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))


            verify { mockTodoService.updateContent(1, updateTodoContentRequest) }
        }

        @Test
        fun `should return updated todo`() {
            every { mockTodoService.updateContent(1, any()) } returns Todo(1, "Learn Spring Boot", false)

            val expectedJson = """
                {
                    "id": 1,
                    "description": "Learn Spring Boot",
                    "finished": false
                }
            """.trimIndent()


            mockMvc.perform(patch("/api/todos/1/content")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(content().json(expectedJson, true))
        }

        @Test
        fun `should preserve finished status when updating content`() {
            every { mockTodoService.updateContent(1, any()) } returns Todo(1, "Learn Spring Boot", true)

            val expectedJson = """
                {
                    "id": 1,
                    "description": "Learn Spring Boot",
                    "finished": true
                }
            """.trimIndent()


            mockMvc.perform(patch("/api/todos/1/content")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(content().json(expectedJson, true))
        }

        @Test
        fun `should return 404 NOT FOUND when todo does not exist`() {
            every { mockTodoService.updateContent(999, any()) } returns null


            mockMvc.perform(patch("/api/todos/999/content")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isNotFound)
        }
    }

}