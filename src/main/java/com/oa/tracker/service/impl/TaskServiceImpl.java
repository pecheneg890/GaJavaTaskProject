package com.oa.tracker.service.impl;

import com.oa.tracker.database.Entity.Task;
import com.oa.tracker.database.Entity.TaskGroup;
import com.oa.tracker.database.Entity.User;
import com.oa.tracker.database.Repository.TaskGroupRepository;
import com.oa.tracker.database.Repository.TaskRepository;
import com.oa.tracker.dto.exception.NotFoundException;
import com.oa.tracker.dto.exception.WrongArgumentException;
import com.oa.tracker.mapper.TaskMapper;
import com.oa.tracker.dto.response.TaskResponseDto;
import com.oa.tracker.dto.request.TaskRequestDto;
import com.oa.tracker.service.TaskService;
import com.oa.tracker.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.oa.tracker.database.categories.Status;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final TaskGroupRepository taskGroupRepository;
    private final UserService userService;

    TaskServiceImpl(TaskRepository taskRepository,
                    TaskMapper taskMapper,
                    TaskGroupRepository taskGroupRepository,
                    UserService userService){
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.taskGroupRepository = taskGroupRepository;
        this.userService = userService;
    }

    /***
     * Все задачи текущего пользователя
     * @return
     */
    @Override
    public List<TaskResponseDto> getTasks() throws WrongArgumentException {
        User user = this.userService.getCurrentUser();
        return this.taskRepository.findByUser(user).stream().map(taskMapper::mapEntityToDto).toList();
    }

    /***
     * Считать задачу текущего пользователя
     * @param id
     * @return
     * @throws NotFoundException
     * @throws WrongArgumentException
     */
    @Override
    public TaskResponseDto getTask(int id) throws NotFoundException, WrongArgumentException {
        User user = this.userService.getCurrentUser();
        Optional<Task> task = this.taskRepository.findByIdAndUser(id, user);
        if (task.isEmpty()) {
            throw new NotFoundException("Задача " + id + " не найдена");
        }
        return taskMapper.mapEntityToDto(task.get());
    }

    /**
     * Создание задачи
     * @param taskRequestDto
     * @return
     * @throws WrongArgumentException
     */
    @Override
    public TaskResponseDto createTask(TaskRequestDto taskRequestDto) throws WrongArgumentException {
        Task task = new Task();
        task = taskMapper.mapDtoToEntity(taskRequestDto, task);
        TaskGroup taskGroup = taskGroupRepository.findById(taskRequestDto.getTaskGroupId()).orElseThrow(
                () -> new WrongArgumentException("Группа задач " + taskRequestDto.getTaskGroupId() + " не найдена")
        );
        task.setTaskGroup(taskGroup);

        User user = userService.getCurrentUser();
        task.setUser(user);

        Task savedTask = this.taskRepository.save(task);
        return taskMapper.mapEntityToDto(savedTask);
    }

    /**
     * Удаление задачи текущего пользователя
     * @param id
     * @throws WrongArgumentException
     */
    @Override
    public void deleteTask(int id) throws WrongArgumentException, NotFoundException {
        User user = userService.getCurrentUser();
        long count = this.taskRepository.deleteByIdAndUser(id, user);
        if (count < 1) throw new NotFoundException("Задача " + id + " не найдена");
    }

    /**
     * Обновление задачи текущего пользователя
     * @param id
     * @param taskRequestDto
     * @return
     * @throws NotFoundException
     * @throws WrongArgumentException
     */

    @Override
    @Transactional
    public TaskResponseDto updateTask(int id, TaskRequestDto taskRequestDto) throws NotFoundException, WrongArgumentException {
        User user = userService.getCurrentUser();
        Task task = taskRepository.findByIdAndUser(id, user).orElseThrow(
                () -> new NotFoundException("Задача " + id + " не найдена")
        );

        task = taskMapper.mapDtoToEntity(taskRequestDto, task);
        Task savedTask = this.taskRepository.save(task);
        return taskMapper.mapEntityToDto(savedTask);
    }

    /**
     * Установка статуса задачи
     * @param id
     * @param status
     * @throws NotFoundException
     * @throws WrongArgumentException
     */
    @Override
    @Transactional
    public void setStatus(int id, Status status) throws NotFoundException, WrongArgumentException {
        User user = userService.getCurrentUser();
        Optional<Task> task = this.taskRepository.findByIdAndUser(id, user);
        if (task.isEmpty()) {
            throw new NotFoundException("Задача " + id + " не найдена");
        }

        task.get().setStatus(status);
        this.taskRepository.save(task.get());
    }
}
