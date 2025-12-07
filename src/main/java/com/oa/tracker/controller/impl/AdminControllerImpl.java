package com.oa.tracker.controller.impl;

import com.oa.tracker.controller.AdminController;
import com.oa.tracker.dto.response.TaskGroupResponseDto;
import com.oa.tracker.dto.response.TaskResponseDto;
import com.oa.tracker.dto.response.TaskStatResponseDto;
import com.oa.tracker.dto.response.UserResponseDto;
import com.oa.tracker.dto.exception.NotFoundException;
import com.oa.tracker.service.AdminService;
import com.oa.tracker.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@SecurityRequirement(name="Auth")
@Tag(name="Консоль администратора")
public class AdminControllerImpl implements AdminController {
    private final AdminService adminService;
    private final UserService userService;

    public AdminControllerImpl(AdminService adminService, UserService userService){
        this.adminService = adminService;
        this.userService = userService;
    }

    @Override
    @GetMapping("/user-list")
    @Operation(summary = "Список всех пользователей")
    public List<UserResponseDto> getUserList() {
        return userService.list();
    }

    @Override
    @Operation(summary = "Список всех задач")
    @GetMapping("/task-list")
    public List<TaskResponseDto> getTaskList() {
        return adminService.getTaskList();
    }

    @GetMapping("/task-user-list/{email}")
    @Operation(summary = "Задачи заданного пользователя")
    @Override
    public List<TaskResponseDto> getTaskByUser(@PathVariable String email) throws NotFoundException {
        return adminService.getTaskByUser(email);
    }

    @GetMapping("/task-group-list")
    @Operation(summary = "Список всех групп задач")
    @Override
    public List<TaskGroupResponseDto> getTaskGroup() {
        return adminService.getTaskGroup();
    }

    @GetMapping("/stat")
    @Operation(summary = "Статистика кол-ва задач в разрезе пользователя и статуса")
    @Override
    public List<TaskStatResponseDto> getStatistic() {
        return adminService.getStatistic();
    }

    @Override
    @GetMapping("/user-set-admin")
    @Operation(summary = "Установка заданному пользователю роли администратор")
    public void setAdmin(String email) throws NotFoundException {
        userService.setAdmin(email);
    }
}
