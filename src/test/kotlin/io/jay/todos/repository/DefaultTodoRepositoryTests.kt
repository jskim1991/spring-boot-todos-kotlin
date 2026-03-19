package io.jay.todos.repository

import io.jay.todos.entity.TodoEntity
import io.jay.todos.model.Todo
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.nullValue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.util.Optional

class DefaultTodoRepositoryTests {

    private lateinit var todoRepository: TodoRepository
    private lateinit var mockTodoJpaRepository: TodoJpaRepository

    @BeforeEach
    fun setUp() {
        mockTodoJpaRepository = mockk()
        todoRepository = DefaultTodoRepository(mockTodoJpaRepository)
    }

    @Nested
    inner class FindAll {
        @Test
        fun `should call jpa repository to find all`() {
            every { mockTodoJpaRepository.findAll() } returns emptyList()


            todoRepository.findAll()


            verify { mockTodoJpaRepository.findAll() }
        }

        @Test
        fun `should return list of todo`() {
            every { mockTodoJpaRepository.findAll() } returns listOf(
                TodoEntity(1, "Learn Kotlin", true)
            )


            val actual = todoRepository.findAll()


            assertThat(actual.size, equalTo(1))
            val todo = actual[0]
            assertThat(todo.id, equalTo(1))
            assertThat(todo.description, equalTo("Learn Kotlin"))
            assertThat(todo.finished, equalTo(true))
        }
    }

    @Nested
    inner class Save {

        val entityToSave = TodoEntity(null, "Learn Kotlin", false)

        @Test
        fun `should call jpa repository to save`() {
            every { mockTodoJpaRepository.save(entityToSave) } returns TodoEntity(1, "Learn Kotlin", false)


            todoRepository.save(Todo(null, "Learn Kotlin", false))


            verify { mockTodoJpaRepository.save(entityToSave) }
        }

        @Test
        fun `should return created todo`() {
            every { mockTodoJpaRepository.save(entityToSave) } returns TodoEntity(1, "Learn Kotlin", false)


            val actual = todoRepository.save(Todo(null, "Learn Kotlin", false))


            assertThat(actual.id, equalTo(1))
            assertThat(actual.description, equalTo("Learn Kotlin"))
            assertThat(actual.finished, equalTo(false))
        }
    }

    @Nested
    inner class FindById {
        @Test
        fun `should call jpa repository to find by id`() {
            every { mockTodoJpaRepository.findById(1) } returns Optional.of(TodoEntity(1, "Learn Kotlin", false))


            todoRepository.findById(1)


            verify { mockTodoJpaRepository.findById(1) }
        }

        @Test
        fun `should return todo when found`() {
            every { mockTodoJpaRepository.findById(1) } returns Optional.of(TodoEntity(1, "Learn Kotlin", true))


            val actual = todoRepository.findById(1)


            assertThat(actual?.id, equalTo(1))
            assertThat(actual?.description, equalTo("Learn Kotlin"))
            assertThat(actual?.finished, equalTo(true))
        }

        @Test
        fun `should return null when not found`() {
            every { mockTodoJpaRepository.findById(999) } returns Optional.empty()


            val actual = todoRepository.findById(999)


            assertThat(actual, nullValue())
        }
    }

    @Nested
    inner class Update {
        @Test
        fun `should call jpa repository to find and save when todo exists`() {
            val existingEntity = TodoEntity(1, "Old description", false)
            val updatedEntity = TodoEntity(1, "Updated description", false)
            every { mockTodoJpaRepository.findById(1) } returns Optional.of(existingEntity)
            every { mockTodoJpaRepository.save(updatedEntity) } returns updatedEntity


            todoRepository.update(1, "Updated description")


            verify { mockTodoJpaRepository.findById(1) }
            verify { mockTodoJpaRepository.save(updatedEntity) }
        }

        @Test
        fun `should return updated todo when todo exists`() {
            val existingEntity = TodoEntity(1, "Old description", true)
            val updatedEntity = TodoEntity(1, "Updated description", true)
            every { mockTodoJpaRepository.findById(1) } returns Optional.of(existingEntity)
            every { mockTodoJpaRepository.save(updatedEntity) } returns updatedEntity


            val actual = todoRepository.update(1, "Updated description")


            assertThat(actual?.id, equalTo(1))
            assertThat(actual?.description, equalTo("Updated description"))
            assertThat(actual?.finished, equalTo(true))
        }

        @Test
        fun `should return null when todo does not exist`() {
            every { mockTodoJpaRepository.findById(999) } returns Optional.empty()


            val actual = todoRepository.update(999, "Updated description")


            assertThat(actual, nullValue())
        }
    }
}