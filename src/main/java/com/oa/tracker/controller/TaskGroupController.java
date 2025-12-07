package com.oa.tracker.controller;

import com.oa.tracker.dto.request.TaskGroupRequestDto;
import com.oa.tracker.dto.response.TaskGroupResponseDto;
import com.oa.tracker.dto.exception.NotFoundException;
import com.oa.tracker.dto.exception.WrongArgumentException;

import java.util.List;

public interface TaskGroupController {
        TaskGroupResponseDto create(TaskGroupRequestDto taskGroupRequestDto) throws WrongArgumentException;

        TaskGroupResponseDto update(int id, TaskGroupRequestDto taskGroupRequest) throws WrongArgumentException, NotFoundException;

        void delete(int id) throws WrongArgumentException, NotFoundException;

        List<TaskGroupResponseDto> getAll() throws WrongArgumentException;
}
