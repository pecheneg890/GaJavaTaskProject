package com.oa.tracker.mapper;

import com.oa.tracker.database.Entity.Task;
import com.oa.tracker.dto.request.TaskRequestDto;
import com.oa.tracker.dto.response.TaskResponseDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TaskMapper {

    public TaskResponseDto mapEntityToDto(Task taskEntity) {
        TaskResponseDto taskRespDto = new TaskResponseDto();
        taskRespDto.setId(taskEntity.getId());
        taskRespDto.setName(taskEntity.getName());
        taskRespDto.setDescription(taskEntity.getDescription());
        taskRespDto.setDeadline(taskEntity.getDeadline());
        taskRespDto.setPriority(taskEntity.getPriority());
        taskRespDto.setStatus(taskEntity.getStatus());
        taskRespDto.setComplexity(taskEntity.getComplexity());
        taskRespDto.setUser(taskEntity.getUser().getUsername());
        taskRespDto.setCreatedAt(taskEntity.getCreatedAt());
        taskRespDto.setTaskGroupId(taskEntity.getTaskGroup().getId());
        taskRespDto.setTaskGroupName(taskEntity.getTaskGroup().getName());
        return taskRespDto;
    }

    public Task mapDtoToEntity(TaskRequestDto taskRequestDto, Task task){
        task.setName(taskRequestDto.getName());
        task.setDescription(taskRequestDto.getDescription());
        task.setDeadline(taskRequestDto.getDeadline());
        task.setPriority(taskRequestDto.getPriority());
        task.setStatus(taskRequestDto.getStatus());
        task.setComplexity(taskRequestDto.getComplexity());
        return task;
    }
}
