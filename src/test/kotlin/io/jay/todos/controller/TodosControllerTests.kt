package io.jay.todos.controller

import io.jay.todos.model.Todo
import io.jay.todos.service.TodosService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders

class TodosControllerTests {

    private lateinit var mockMvc: MockMvc
    private lateinit var mockTodosService: TodosService

    @BeforeEach
    fun setUp() {
        mockTodosService = mockk()
        mockMvc = MockMvcBuilders.standaloneSetup(TodosController(mockTodosService)).build()
    }

    @Test
    fun `get should return 200 OK`() {
        every { mockTodosService.getAll() } returns emptyList()


        mockMvc.get("/api/todos")
            .andExpect {
                status { isOk() }
            }
    }

    @Test
    fun `get should call todos service`() {
        every { mockTodosService.getAll() } returns emptyList()


        mockMvc.get("/api/todos")


        verify { mockTodosService.getAll() }
    }

    @Test
    fun `get should return list of todos`() {
        every { mockTodosService.getAll() } returns listOf(
            Todo(1, "Learn Kotlin", true)
        )


        mockMvc.get("/api/todos")
            .andExpect {
                content { contentType(MediaType.APPLICATION_JSON) }
                content { jsonPath("$.length()", equalTo(1)) }
                content { jsonPath("$[0].id", equalTo(1)) }
                content { jsonPath("$[0].description", equalTo("Learn Kotlin")) }
                content { jsonPath("$[0].finished", equalTo(true)) }
            }
    }
}