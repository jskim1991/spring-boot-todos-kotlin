package io.jay.todos.controller

import io.jay.todos.controller.dto.NewTodoRequest
import io.jay.todos.controller.dto.TodoResponse
import io.jay.todos.service.TodoService
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.server.ResponseStatusException

@Controller
@ResponseBody
@RequestMapping("/api/todos")
class TodosController(private val todoService: TodoService) {

    @GetMapping
    fun getAll(): List<TodoResponse> {
        return todoService.getAll().map {
            TodoResponse(it.id!!, it.description, it.finished)
        }
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Int): TodoResponse {
        val todo = todoService.getById(id)
            ?: throw ResponseStatusException(NOT_FOUND, "Todo with id $id not found")
        return TodoResponse(todo.id!!, todo.description, todo.finished)
    }

    @PostMapping
    @ResponseStatus(CREATED)
    fun createTodo(@RequestBody newTodoRequest: NewTodoRequest): TodoResponse {
        val created = todoService.create(newTodoRequest)
        return TodoResponse(created.id!!, created.description, created.finished)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    fun deleteTodo(@PathVariable id: Int) {
        if (!todoService.delete(id)) {
            throw ResponseStatusException(NOT_FOUND, "Todo with id $id not found")
        }
    }

}