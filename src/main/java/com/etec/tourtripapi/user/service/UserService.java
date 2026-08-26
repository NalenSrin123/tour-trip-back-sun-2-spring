package com.etec.tourtripapi.user.service;

import com.etec.tourtripapi.user.dto.request.CreateUserRequest;
import com.etec.tourtripapi.user.dto.request.UpdateUserRequest;
import com.etec.tourtripapi.user.dto.response.UserResponse;
import java.util.List;

public interface UserService {
    UserResponse register(CreateUserRequest request);
    UserResponse update(Integer id, UpdateUserRequest request);
    UserResponse getById(Integer id);
    List<UserResponse> getAll();
    void delete(Integer id);
}