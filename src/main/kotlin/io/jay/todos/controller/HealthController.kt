package io.jay.todos.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@ResponseBody
class HealthController {

    @GetMapping("/health")
    fun health() {

    }
}