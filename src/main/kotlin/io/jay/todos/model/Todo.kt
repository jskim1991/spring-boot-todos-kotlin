package io.jay.todos.model

data class Todo(val id: Int?,
           val description: String,
           val finished: Boolean,
           val label: String? = null) {

    constructor(description: String) : this(null, description, false)
}