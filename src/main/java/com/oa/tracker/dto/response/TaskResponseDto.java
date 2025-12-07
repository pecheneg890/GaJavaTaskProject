package com.oa.tracker.dto.response;

import com.oa.tracker.database.categories.Complexity;
import com.oa.tracker.database.categories.Priority;
import com.oa.tracker.database.categories.Status;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Setter
@Getter
public class TaskResponseDto {
    Long id;
    String name;
    String description;
    Date deadline;
    Priority priority;
    Status status;
    Complexity complexity;
    String user;
    LocalDateTime createdAt;
    long taskGroupId;
    String taskGroupName;
}

