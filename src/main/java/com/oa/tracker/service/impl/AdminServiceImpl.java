package com.oa.tracker.service.impl;

import com.oa.tracker.database.Entity.User;
import com.oa.tracker.database.Repository.TaskGroupRepository;
import com.oa.tracker.database.Repository.TaskRepository;
import com.oa.tracker.dto.response.TaskGroupResponseDto;
import com.oa.tracker.dto.response.TaskResponseDto;
import com.oa.tracker.dto.response.TaskStatResponseDto;
import com.oa.tracker.dto.exception.NotFoundException;
import com.oa.tracker.mapper.TaskGroupMapper;
import com.oa.tracker.mapper.TaskMapper;
import com.oa.tracker.service.AdminService;
import com.oa.tracker.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    private final UserService userService;
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final TaskGroupRepository taskGroupRepository;
    private final TaskGroupMapper taskGroupMapper;

    AdminServiceImpl(UserService userService,
                     TaskRepository taskRepository,
                     TaskMapper taskMapper,
                     TaskGroupRepository taskGroupRepository,
                     TaskGroupMapper taskGroupMapper){
        this.userService = userService;
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.taskGroupRepository = taskGroupRepository;
        this.taskGroupMapper = taskGroupMapper;
    }

    /**
     * Получение списка всех задач
     * @return
     */
    @Override
    public List<TaskResponseDto> getTaskList(){
        return taskRepository.findAll().stream().map(taskMapper::mapEntityToDto).toList();
    }

    /**
     * Задачи по пользователю
     * @param email
     * @return
     * @throws NotFoundException
     */
    @Override
    public List<TaskResponseDto> getTaskByUser(String email) throws NotFoundException {
        User user = this.userService.getUser(email);
        return taskRepository.findByUser(user).stream().map(taskMapper::mapEntityToDto).toList();
    }

    /**
     * Все группы задач
     * @return
     */
    @Override
    public List<TaskGroupResponseDto> getTaskGroup(){
        return this.taskGroupRepository.findAll().stream().map(taskGroupMapper::mapEntityToDto).toList();
    }

    /**
     * Статистика по задачам (кол-во задач в разрезе пользователя и статуса)
     * @return
     */
    @Override
    public List<TaskStatResponseDto> getStatistic(){
        return this.taskRepository.getStatistic();
    }
}
