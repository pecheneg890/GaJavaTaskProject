package com.oa.tracker.dto.response;

import com.oa.tracker.database.categories.Role;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
public class UserResponseDto {
    public String email;
    public String firstName;
    public String lastName;
    public Role role;
    public LocalDateTime createdAt;
}
