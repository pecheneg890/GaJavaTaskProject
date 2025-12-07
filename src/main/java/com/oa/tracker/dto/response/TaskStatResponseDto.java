package com.oa.tracker.dto.response;

import com.oa.tracker.database.categories.Status;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TaskStatResponseDto {
    public TaskStatResponseDto(String user, Status status, long taskCount){
        this.user = user;
        this.status = status;
        this.taskCount = taskCount;
    }
    String user;
    Status status;
    long taskCount;
}
