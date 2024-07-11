package io.jay.todos.controller

import io.jay.todos.controller.dto.TodoResponse
import io.jay.todos.service.TodosService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@ResponseBody
@RequestMapping("/api")
class TodosController(private val todosService: TodosService) {

    @GetMapping("/todos")
    fun getAll(): List<TodoResponse> {
        return todosService.getAll().map {
            TodoResponse(it.id, it.description, it.finished)
        }
    }
}