package com.oa.tracker.mapper;

import com.oa.tracker.database.Entity.User;
import com.oa.tracker.dto.request.UserRequestDto;
import com.oa.tracker.dto.response.UserResponseDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper {
    public UserResponseDto asResponse(User user) {
        return new UserResponseDto(
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getRole(),
                user.getCreatedAt()
        );
    }

    public User update(User user, UserRequestDto request) {
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        return user;
    }

    public List<UserResponseDto> asListResponse(Iterable<User> users) {
        List<UserResponseDto> response = new ArrayList<>();
        for (User user : users) {
            response.add(asResponse(user));
        }
        return response;
    }
}
