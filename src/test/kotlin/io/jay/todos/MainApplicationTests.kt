package io.jay.todos

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.jay.todos.controller.dto.TodoResponse
import io.jay.todos.entity.TodoEntity
import io.jay.todos.repository.TodoJpaRepository
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get

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

    @Test
    fun contextLoads() {
    }

    @Test
    fun `end to end`() {
        todoJpaRepository.save(TodoEntity(null, "Learn Kotlin", true))


        var jsonResponse = mockMvc.perform(get("/api/todos"))
            .andReturn()
            .response
            .contentAsString


        val result = objectMapper.readValue<List<TodoResponse>>(jsonResponse)
        assertThat(result.size, equalTo(1))

        val actual = result.get(0)
        assertThat(actual.id, equalTo(1))
        assertThat(actual.description, equalTo("Learn Kotlin"))
        assertThat(actual.finished, equalTo(true))
    }
}
