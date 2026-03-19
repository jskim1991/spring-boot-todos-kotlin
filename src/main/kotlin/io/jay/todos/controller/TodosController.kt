package io.jay.todos.controller

import io.jay.todos.controller.dto.NewTodoRequest
import io.jay.todos.controller.dto.TodoResponse
import io.jay.todos.controller.dto.UpdateTodoDescriptionRequest
import io.jay.todos.service.TodoService
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

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

    @PostMapping
    @ResponseStatus(CREATED)
    fun createTodo(@RequestBody newTodoRequest: NewTodoRequest): TodoResponse {
        val created = todoService.create(newTodoRequest)
        return TodoResponse(created.id!!, created.description, created.finished)
    }

    @PutMapping("/{id}")
    fun updateTodoDescription(@PathVariable id: Int, @RequestBody updateRequest: UpdateTodoDescriptionRequest): TodoResponse? {
        val updated = todoService.updateDescription(id, updateRequest.description)
        return if (updated != null) {
            TodoResponse(updated.id!!, updated.description, updated.finished)
        } else {
            null
        }
    }

}