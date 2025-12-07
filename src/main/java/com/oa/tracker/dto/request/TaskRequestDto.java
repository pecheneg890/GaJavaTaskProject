package com.oa.tracker.dto.request;

import com.oa.tracker.database.categories.Complexity;
import com.oa.tracker.database.categories.Priority;
import com.oa.tracker.database.categories.Status;
import lombok.Data;

import java.util.Date;

@Data
public class TaskRequestDto {
    String name;
    String description;
    Date deadline;
    Priority priority;
    Status status;
    Complexity complexity;
    int taskGroupId;
}
