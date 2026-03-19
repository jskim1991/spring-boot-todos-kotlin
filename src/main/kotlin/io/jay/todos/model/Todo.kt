package io.jay.todos.model

data class Todo(val id: Int?,
           val description: String,
           val finished: Boolean,
           val priority: Priority? = null) {

    constructor(description: String) : this(null, description, false)
}