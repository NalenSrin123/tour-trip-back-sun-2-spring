package com.etec.tourtripapi.role.service;

import com.etec.tourtripapi.common.exception.ResourceNotFoundException;
import com.etec.tourtripapi.role.dto.RoleRequest;
import com.etec.tourtripapi.role.dto.RoleResponse;
import com.etec.tourtripapi.role.entity.Role;
import com.etec.tourtripapi.role.mapper.RoleMapper;
import com.etec.tourtripapi.role.repository.RoleRepository;
import com.etec.tourtripapi.user.entity.User;
import com.etec.tourtripapi.user.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final RoleMapper roleMapper;

    @Override
    public RoleResponse create(RoleRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User " + request.getUserId() + " not found"));
        Role role = Role.builder()
                .user(user)
                .name(request.getName())
                .type(request.getType())
                .build();
        return roleMapper.toResponse(roleRepository.save(role));
    }

    @Override
    public RoleResponse update(Integer id, RoleRequest request) {
        Role role = findRoleOrThrow(id);
        role.setName(request.getName());
        role.setType(request.getType());
        return roleMapper.toResponse(roleRepository.save(role));
    }

    @Override
    @Transactional(readOnly = true)
    public RoleResponse getById(Integer id) {
        return roleMapper.toResponse(findRoleOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleResponse> getByUserId(Integer userId) {
        return roleMapper.toResponseList(roleRepository.findByUserId(userId));
    }

    @Override
    public void delete(Integer id) {
        roleRepository.delete(findRoleOrThrow(id));
    }

    private Role findRoleOrThrow(Integer id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role " + id + " not found"));
    }
}