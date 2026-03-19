package io.jay.todos

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.jay.todos.controller.dto.TodoResponse
import io.jay.todos.controller.dto.UpdateTodoRequest
import io.jay.todos.entity.TodoEntity
import io.jay.todos.repository.TodoJpaRepository
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class MainApplicationTests {

    @Autowired
    private lateinit var todoJpaRepository: TodoJpaRepository

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @AfterEach
    fun cleanup() {
        todoJpaRepository.deleteAll()
    }

    @Test
    fun contextLoads() {
    }

    @Test
    fun `end to end`() {
        val savedTodo = todoJpaRepository.save(TodoEntity(null, "Learn Kotlin", true))


        var jsonResponse = mockMvc.perform(get("/api/todos"))
            .andReturn()
            .response
            .contentAsString


        val result = objectMapper.readValue<List<TodoResponse>>(jsonResponse)
        assertThat(result.size, equalTo(1))

        val actual = result.get(0)
        assertThat(actual.id, equalTo(savedTodo.id))
        assertThat(actual.description, equalTo("Learn Kotlin"))
        assertThat(actual.finished, equalTo(true))
    }

    @Test
    fun `should update todo description via PUT endpoint`() {
        val savedTodo = todoJpaRepository.save(TodoEntity(null, "Learn Kotlin", false))
        val updateRequest = UpdateTodoRequest("Learn Spring Boot")
        val requestBody = objectMapper.writeValueAsString(updateRequest)


        val jsonResponse = mockMvc.perform(put("/api/todos/${savedTodo.id}")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andReturn()
            .response
            .contentAsString


        val result = objectMapper.readValue<TodoResponse>(jsonResponse)
        assertThat(result.id, equalTo(savedTodo.id))
        assertThat(result.description, equalTo("Learn Spring Boot"))
        assertThat(result.finished, equalTo(false))

        val updatedInDb = todoJpaRepository.findById(savedTodo.id!!).get()
        assertThat(updatedInDb.description, equalTo("Learn Spring Boot"))
        assertThat(updatedInDb.finished, equalTo(false))
    }
}
