package com.oa.tracker.controller;

import com.oa.tracker.database.categories.Status;
import com.oa.tracker.dto.request.TaskRequestDto;
import com.oa.tracker.dto.response.TaskResponseDto;
import com.oa.tracker.dto.exception.NotFoundException;
import com.oa.tracker.dto.exception.WrongArgumentException;

import java.util.List;

public interface TaskController {

    List<TaskResponseDto> getTasks() throws WrongArgumentException;

    TaskResponseDto getTask(int id) throws NotFoundException, WrongArgumentException;

    TaskResponseDto createTask(TaskRequestDto taskRequestDto) throws WrongArgumentException;

    void deleteTask(int id) throws WrongArgumentException, NotFoundException;

    TaskResponseDto updateTask(int id, TaskRequestDto taskRequestDto) throws NotFoundException, WrongArgumentException;

    void setStatus(int id, Status status) throws NotFoundException, WrongArgumentException;
}
