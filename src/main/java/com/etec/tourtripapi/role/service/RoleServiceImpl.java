package com.etec.tourtripapi.role.service;

import com.etec.tourtripapi.common.exception.DuplicateResourceException;
import com.etec.tourtripapi.common.exception.ResourceNotFoundException;
import com.etec.tourtripapi.role.dto.AssignRoleRequest;
import com.etec.tourtripapi.role.dto.RoleRequest;
import com.etec.tourtripapi.role.dto.RoleResponse;
import com.etec.tourtripapi.role.dto.UserRoleResponse;
import com.etec.tourtripapi.role.entity.Role;
import com.etec.tourtripapi.role.entity.UserRole;
import com.etec.tourtripapi.role.mapper.RoleMapper;
import com.etec.tourtripapi.role.repository.RoleRepository;
import com.etec.tourtripapi.role.repository.UserRoleRepository;
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
    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;
    private final RoleMapper roleMapper;

    @Override
    public RoleResponse create(RoleRequest request) {
        if (roleRepository.existsByName(request.getName())) {
            throw new DuplicateResourceException(
                    "Role '" + request.getName() + "' already exists");
        }
        Role role = Role.builder()
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
    public List<RoleResponse> getAll() {
        return roleMapper.toResponseList(roleRepository.findAll());
    }

    @Override
    public void delete(Integer id) {
        roleRepository.delete(findRoleOrThrow(id));
    }

    @Override
    public UserRoleResponse assignRoleToUser(AssignRoleRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User " + request.getUserId() + " not found"));
        Role role = findRoleOrThrow(request.getRoleId());

        if (userRoleRepository.existsByUserIdAndRoleId(request.getUserId(), request.getRoleId())) {
            throw new DuplicateResourceException("User already has this role");
        }
        UserRole userRole = UserRole.builder().user(user).role(role).build();
        return roleMapper.toUserRoleResponse(userRoleRepository.save(userRole));
    }

    @Override
    public void removeRoleFromUser(AssignRoleRequest request) {
        userRoleRepository.deleteByUserIdAndRoleId(request.getUserId(), request.getRoleId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserRoleResponse> getRolesByUserId(Integer userId) {
        return roleMapper.toUserRoleResponseList(userRoleRepository.findByUserId(userId));
    }

    private Role findRoleOrThrow(Integer id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role " + id + " not found"));
    }
}