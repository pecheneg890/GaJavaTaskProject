package com.oa.tracker.service;

import com.oa.tracker.dto.response.TaskGroupResponseDto;
import com.oa.tracker.dto.response.TaskResponseDto;
import com.oa.tracker.dto.response.TaskStatResponseDto;
import com.oa.tracker.dto.exception.NotFoundException;

import java.util.List;

public interface AdminService {
    List<TaskResponseDto> getTaskList();

    List<TaskResponseDto> getTaskByUser(String email) throws NotFoundException;

    List<TaskGroupResponseDto> getTaskGroup();

    List<TaskStatResponseDto> getStatistic();
}
