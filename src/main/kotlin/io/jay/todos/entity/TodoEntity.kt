package io.jay.todos.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "todos")
class TodoEntity(
    @Id @GeneratedValue val id: Int?,
    val description: String,
    val finished: Boolean) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TodoEntity) return false

        if (id != other.id) return false
        if (description != other.description) return false
        if (finished != other.finished) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id ?: 0
        result = 31 * result + description.hashCode()
        result = 31 * result + finished.hashCode()
        return result
    }
}