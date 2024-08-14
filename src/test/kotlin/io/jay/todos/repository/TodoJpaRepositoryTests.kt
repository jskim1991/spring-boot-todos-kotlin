package io.jay.todos.repository

import io.jay.todos.entity.TodoEntity
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class TodoJpaRepositoryTests {

    @Autowired
    private lateinit var todoJpaRepository: TodoJpaRepository

    @BeforeEach
    fun setUp() {
        todoJpaRepository.save(TodoEntity(null, "Learn Kotlin", true))
    }

    @Test
    fun `findAll returns all records`() {
        val actual = todoJpaRepository.findAll()


        assertThat(actual.size, equalTo(1))
    }

    @Test
    fun `two identical must be equal`() {
        val one = TodoEntity(1, "Learn Kotlin", true)
        val two = TodoEntity(1, "Learn Kotlin", true)


        assertThat(one == two, equalTo(true))
    }
}