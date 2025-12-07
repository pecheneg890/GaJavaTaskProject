package com.oa.tracker.mapper;

import com.oa.tracker.database.Entity.TaskGroup;
import com.oa.tracker.dto.request.TaskGroupRequestDto;
import com.oa.tracker.dto.response.TaskGroupResponseDto;
import org.springframework.stereotype.Component;

@Component
public class TaskGroupMapper {

    public TaskGroupResponseDto mapEntityToDto(TaskGroup taskGroupEntity) {
        TaskGroupResponseDto taskRespDto = new TaskGroupResponseDto();
        taskRespDto.setId(taskGroupEntity.getId());
        taskRespDto.setName(taskGroupEntity.getName());
        taskRespDto.setDescription(taskGroupEntity.getDescription());
        taskRespDto.setCategory(taskGroupEntity.getCategory());
        taskRespDto.setUser(taskGroupEntity.getUser().getUsername());
        return taskRespDto;
    }

    public TaskGroup mapDtoToEntity(TaskGroupRequestDto taskGroupRequestDto, TaskGroup taskGroup){
        taskGroup.setName(taskGroupRequestDto.getName());
        taskGroup.setDescription(taskGroupRequestDto.getDescription());
        taskGroup.setCategory(taskGroupRequestDto.getCategory());
        return taskGroup;
    }
}
