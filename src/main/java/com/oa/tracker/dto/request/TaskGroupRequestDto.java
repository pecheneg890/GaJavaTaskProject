package com.oa.tracker.dto.request;

import com.oa.tracker.database.categories.Category;
import lombok.Getter;

@Getter
public class TaskGroupRequestDto {
    String name;
    String description;
    Category category;
}
