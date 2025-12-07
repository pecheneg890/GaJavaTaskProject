package com.oa.tracker.dto.response;

import com.oa.tracker.database.categories.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class TaskGroupResponseDto {
    long id;
    String name;
    String description;
    String user;
    Category category;
}
