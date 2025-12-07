package com.oa.tracker.service;

import com.oa.tracker.database.Entity.User;
import com.oa.tracker.dto.request.UserRequestDto;
import com.oa.tracker.dto.response.UserResponseDto;
import com.oa.tracker.dto.exception.AlreadyExistsException;
import com.oa.tracker.dto.exception.NotFoundException;
import com.oa.tracker.dto.exception.WrongArgumentException;

import java.util.List;

public interface UserService {
    List<UserResponseDto> list();

    UserResponseDto getByEmail(String email) throws NotFoundException;

    void create(User user) throws AlreadyExistsException, WrongArgumentException;

    UserResponseDto update(String email, UserRequestDto request) throws NotFoundException;

    User getCurrentUser() throws WrongArgumentException;

    User getUser(String username) throws NotFoundException;

    void setAdmin(String username) throws NotFoundException;

    void changePassword(String password) throws WrongArgumentException;
}
