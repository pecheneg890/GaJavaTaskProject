package com.oa.tracker.service.impl;

import com.oa.tracker.database.Entity.TaskGroup;
import com.oa.tracker.database.Entity.User;
import com.oa.tracker.database.Repository.TaskGroupRepository;
import com.oa.tracker.dto.request.TaskGroupRequestDto;
import com.oa.tracker.dto.response.TaskGroupResponseDto;
import com.oa.tracker.dto.exception.NotFoundException;
import com.oa.tracker.dto.exception.WrongArgumentException;
import com.oa.tracker.mapper.TaskGroupMapper;
import com.oa.tracker.service.TaskGroupService;
import com.oa.tracker.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskGroupServiceImpl implements TaskGroupService {
    private final TaskGroupMapper taskGroupMapper;
    private final UserService userService;
    private final TaskGroupRepository taskGroupRepository;

    public TaskGroupServiceImpl(TaskGroupMapper taskGroupMapper,
                                UserService userService,
                                TaskGroupRepository taskGroupRepository){
        this.taskGroupMapper = taskGroupMapper;
        this.userService = userService;
        this.taskGroupRepository = taskGroupRepository;
    }

    /**
     * Создание группы задач
     * @param taskGroupRequestDto
     * @return
     * @throws WrongArgumentException
     */
    @Override
    public TaskGroupResponseDto create(TaskGroupRequestDto taskGroupRequestDto) throws WrongArgumentException {
        TaskGroup taskGroup = new TaskGroup();
        taskGroup = taskGroupMapper.mapDtoToEntity(taskGroupRequestDto, taskGroup);

        User user = userService.getCurrentUser();
        taskGroup.setUser(user);

        TaskGroup savedTaskGroup = this.taskGroupRepository.save(taskGroup);
        return taskGroupMapper.mapEntityToDto(savedTaskGroup);
    }

    /**
     * Изменение группы задач
     * @param id
     * @param taskGroupRequest
     * @return
     * @throws WrongArgumentException
     * @throws NotFoundException
     */
    @Override
    public TaskGroupResponseDto update(int id, TaskGroupRequestDto taskGroupRequest) throws WrongArgumentException, NotFoundException {
        User user = userService.getCurrentUser();
        TaskGroup taskGroup = taskGroupRepository.findByIdAndUser(id, user).orElseThrow(
                () -> new NotFoundException("Группа задач " + id + " не найдена")
        );

        taskGroup = taskGroupMapper.mapDtoToEntity(taskGroupRequest, taskGroup);
        TaskGroup savedTaskGroup = this.taskGroupRepository.save(taskGroup);
        return taskGroupMapper.mapEntityToDto(savedTaskGroup);
    }

    /**
     * Удаление группы задач
     * @param id
     * @throws WrongArgumentException
     * @throws NotFoundException
     */
    @Override
    public void delete(int id) throws WrongArgumentException, NotFoundException {
        User user = userService.getCurrentUser();
        long count = this.taskGroupRepository.deleteByIdAndUser(id, user);
        if (count < 1) throw new NotFoundException("Группа задач " + id + " не найдена");
    }

    /**
     * Получение списка своих задач
     * @return
     * @throws WrongArgumentException
     */
    @Override
    public List<TaskGroupResponseDto> getAll() throws WrongArgumentException {
        User user = this.userService.getCurrentUser();
        return this.taskGroupRepository.findByUser(user).stream().map(taskGroupMapper::mapEntityToDto).toList();
    }
}
