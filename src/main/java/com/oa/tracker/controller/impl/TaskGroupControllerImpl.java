package com.oa.tracker.controller.impl;

import com.oa.tracker.controller.TaskGroupController;
import com.oa.tracker.dto.request.TaskGroupRequestDto;
import com.oa.tracker.dto.response.TaskGroupResponseDto;
import com.oa.tracker.dto.exception.NotFoundException;
import com.oa.tracker.dto.exception.WrongArgumentException;
import com.oa.tracker.service.TaskGroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/task-group")
@SecurityRequirement(name="Auth")
@Tag(name="Группа задач")
public class TaskGroupControllerImpl implements TaskGroupController {
    private final TaskGroupService taskGroupService;

    TaskGroupControllerImpl(TaskGroupService taskGroupService){
        this.taskGroupService = taskGroupService;
    }

    @PostMapping
    @Override
    @Operation(summary = "Создание группы задач")
    public TaskGroupResponseDto create(@RequestBody TaskGroupRequestDto taskGroupRequestDto) throws WrongArgumentException {
        return taskGroupService.create(taskGroupRequestDto);
    }

    @PutMapping("/{id}")
    @Override
    @Operation(summary = "Изменение группы задач")
    public TaskGroupResponseDto update(@PathVariable int id, @RequestBody TaskGroupRequestDto taskGroupRequest) throws WrongArgumentException, NotFoundException {
        return taskGroupService.update(id, taskGroupRequest);
    }

    @DeleteMapping("/{id}")
    @Override
    @Operation(summary = "Удаление группы задач")
    public void delete(@PathVariable() int id) throws WrongArgumentException, NotFoundException {
        taskGroupService.delete(id);
    }

    @GetMapping
    @Override
    @Operation(summary = "Получение своих групп задач")
    public List<TaskGroupResponseDto> getAll() throws WrongArgumentException {
        return taskGroupService.getAll();
    }
}
