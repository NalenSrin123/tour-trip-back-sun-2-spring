package com.etec.tourtripapi.user.service;

import com.etec.tourtripapi.common.enums.UserStatus;
import com.etec.tourtripapi.common.exception.DuplicateResourceException;
import com.etec.tourtripapi.common.exception.ResourceNotFoundException;
import com.etec.tourtripapi.user.dto.request.CreateUserRequest;
import com.etec.tourtripapi.user.dto.request.UpdateUserRequest;
import com.etec.tourtripapi.user.dto.response.UserResponse;
import com.etec.tourtripapi.user.entity.User;
import com.etec.tourtripapi.user.mapper.UserMapper;
import com.etec.tourtripapi.user.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse register(CreateUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException(
                    "A user with email '" + request.getEmail() + "' already exists");
        }
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .status(UserStatus.INACTIVE)
                .build();
        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    public UserResponse update(Integer id, UpdateUserRequest request) {
        User user = findUserOrThrow(id);
        user.setName(request.getName());
        user.setPhone(request.getPhone());
        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getById(Integer id) {
        return userMapper.toResponse(findUserOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> getAll() {
        return userMapper.toResponseList(userRepository.findAll());
    }

    @Override
    public void delete(Integer id) {
        userRepository.delete(findUserOrThrow(id));
    }

    private User findUserOrThrow(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User " + id + " not found"));
    }
}