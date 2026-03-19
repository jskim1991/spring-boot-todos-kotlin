package io.jay.todos.entity

import io.jay.todos.model.Priority
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "todo")
class TodoEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Int?,
    val description: String,
    val finished: Boolean,
    @Enumerated(EnumType.STRING) val priority: Priority? = null) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TodoEntity) return false

        if (id != other.id) return false
        if (description != other.description) return false
        if (finished != other.finished) return false
        if (priority != other.priority) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id ?: 0
        result = 31 * result + description.hashCode()
        result = 31 * result + finished.hashCode()
        result = 31 * result + (priority?.hashCode() ?: 0)
        return result
    }
}