package com.oa.tracker.controller.impl;

import com.oa.tracker.controller.TaskController;
import com.oa.tracker.database.categories.Status;
import com.oa.tracker.dto.response.TaskResponseDto;

import com.oa.tracker.dto.request.TaskRequestDto;
import com.oa.tracker.dto.exception.NotFoundException;
import com.oa.tracker.dto.exception.WrongArgumentException;
import com.oa.tracker.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/task")
@SecurityRequirement(name="Auth")
@Tag(name="Задачи")
public class TaskControllerImpl implements TaskController {
    private final TaskService taskService;

    TaskControllerImpl(TaskService taskService){
        this.taskService = taskService;
    }

    @GetMapping()
    @Override
    @Operation(summary = "Получение списка своих задач")
    public List<TaskResponseDto> getTasks() throws WrongArgumentException {
        return taskService.getTasks();
    }

    @GetMapping("/{id}")
    @Override
    @Operation(summary = "Получение задачи по id")
    public TaskResponseDto getTask(@PathVariable() int id) throws NotFoundException, WrongArgumentException {
        return taskService.getTask(id);
    }

    @PostMapping()
    @Override
    @Operation(summary = "Создание задачи")
    public TaskResponseDto createTask(@RequestBody TaskRequestDto taskRequestDto) throws WrongArgumentException {
        return taskService.createTask(taskRequestDto);
    }

    @DeleteMapping("/{id}")
    @Override
    @Operation(summary = "Удаление задачи")
    public void deleteTask(@PathVariable() int id) throws WrongArgumentException, NotFoundException {
        this.taskService.deleteTask(id);
    }


    @PutMapping("/{id}")
    @Override
    @Operation(summary = "Изменение задачи")
    public TaskResponseDto updateTask(@PathVariable() int id, @RequestBody TaskRequestDto taskRequestDto) throws NotFoundException, WrongArgumentException {
        return this.taskService.updateTask(id, taskRequestDto);
    }


    @PostMapping("/{id}/status/{status}")
    @Override
    @Operation(summary = "Изменение статуса задачи")
    public void setStatus(@PathVariable() int id, @PathVariable() Status status) throws NotFoundException, WrongArgumentException {
        this.taskService.setStatus(id, status);
    }
}
