package io.jay.todos.controller

import com.fasterxml.jackson.databind.ObjectMapper
import io.jay.todos.controller.dto.AssignLabelRequest
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
                        "finished": true,
                        "label": null
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
                    "finished": false,
                    "label": null
                }
            """.trimIndent()


            mockMvc.perform(post("/api/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(content().json(expectedJson, true))
        }
    }

    @Nested
    inner class AssignLabel {
        val assignLabelRequest = AssignLabelRequest("work")
        val requestBody = objectMapper.writeValueAsString(assignLabelRequest)

        @Test
        fun `should return 200 OK`() {
            every { mockTodoService.assignLabel(any(), any()) } returns Todo(1, "Learn Kotlin", false, "work")


            mockMvc.perform(patch("/api/todos/1/label")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk)
        }

        @Test
        fun `should call todoService`() {
            every { mockTodoService.assignLabel(1, assignLabelRequest) } returns Todo(1, "Learn Kotlin", false, "work")


            mockMvc.perform(patch("/api/todos/1/label")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))


            verify { mockTodoService.assignLabel(1, assignLabelRequest) }
        }

        @Test
        fun `should return updated todo with label`() {
            every { mockTodoService.assignLabel(any(), any()) } returns Todo(1, "Learn Kotlin", false, "work")


            val expectedJson = """
                {
                    "id": 1,
                    "description": "Learn Kotlin",
                    "finished": false,
                    "label": "work"
                }
            """.trimIndent()


            mockMvc.perform(patch("/api/todos/1/label")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(content().json(expectedJson, true))
        }
    }

}