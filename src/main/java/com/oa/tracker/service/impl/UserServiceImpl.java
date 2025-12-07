package com.oa.tracker.service.impl;

import com.oa.tracker.database.categories.Role;
import com.oa.tracker.database.Entity.User;
import com.oa.tracker.database.Repository.UserRepository;
import com.oa.tracker.dto.request.UserRequestDto;
import com.oa.tracker.dto.response.UserResponseDto;
import com.oa.tracker.dto.exception.AlreadyExistsException;
import com.oa.tracker.dto.exception.NotFoundException;
import com.oa.tracker.dto.exception.WrongArgumentException;
import com.oa.tracker.mapper.UserMapper;
import com.oa.tracker.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {
    private final UserMapper mapper;
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserMapper mapper,
                           UserRepository userRepository,
                           PasswordEncoder passwordEncoder){
        this.repository = userRepository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Список пользователей
     * @return
     */
    @Override
    public List<UserResponseDto> list() {
        return mapper.asListResponse(
                repository.findAll()
        );
    }

    /**
     * Пользователь по email
     * @param email
     * @return
     * @throws NotFoundException
     */
    @Override
    public UserResponseDto getByEmail(String email) throws NotFoundException {
        return mapper.asResponse(getUser(email));
    }

    /**
     * Создание пользователя
     * @param user
     * @throws AlreadyExistsException
     */
    @Override
    public void create(User user) throws AlreadyExistsException, WrongArgumentException {
        if (!validateEmail(user.getUsername())) throw  new WrongArgumentException("email не валиден");
        repository.save(user);
    }

    /**
     * Проверка email
     * @param email
     * @return
     */
    private boolean validateEmail(String email)
    {
        return Pattern.compile("^(.+)@(\\S+)$")
                .matcher(email)
                .matches();
    }

    /**
     * Изменение пользователя
     * @param email
     * @param request
     * @return
     * @throws NotFoundException
     */
    @Transactional
    @Modifying
    @Override
    public UserResponseDto update(String email, UserRequestDto request) throws NotFoundException {
        User user = repository.findById(email).orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        user = mapper.update(user, request);
        return mapper.asResponse(user);
    }

    /**
     * Получение текущего пользователя
     * @return
     * @throws WrongArgumentException
     */
    @Override
    public User getCurrentUser() throws WrongArgumentException{
        Optional<User> user = Optional.empty();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            String email = authentication.getName();
            user = repository.findById(email);
        }

        if (user.isPresent())
            return user.get();
        else
            throw new WrongArgumentException("Пользователь не найден");
    }

    /**
     * Получение данных пользователя
     * @param username
     * @return
     * @throws NotFoundException
     */
    @Override
    public User getUser(String username) throws NotFoundException {
        return repository.findById(username).orElseThrow(
                () -> new NotFoundException("Пользователь не найден")
        );
    }

    /**
     * Установка пользователя администратором
     * @param username
     * @throws NotFoundException
     */
    @Override
    public void setAdmin(String username) throws NotFoundException {
        User user = this.getUser(username);
        user.setRole(Role.ROLE_ADMIN);
        this.repository.save(user);
    }

    /**
     * Изменение пароля
     * @param password
     * @throws WrongArgumentException
     */
    @Override
    public void changePassword(String password) throws WrongArgumentException {
        User user = this.getCurrentUser();
        user.setPasswordHash(passwordEncoder.encode(password));
        this.repository.save(user);
    }
}
