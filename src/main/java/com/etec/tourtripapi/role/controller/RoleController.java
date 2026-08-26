package com.etec.tourtripapi.role.controller;

import com.etec.tourtripapi.common.response.ApiResponse;
import com.etec.tourtripapi.role.dto.RoleRequest;
import com.etec.tourtripapi.role.dto.RoleResponse;
import com.etec.tourtripapi.role.service.RoleService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<ApiResponse<RoleResponse>> create(
            @Valid @RequestBody RoleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(roleService.create(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<RoleResponse>> update(
            @PathVariable Integer id, @Valid @RequestBody RoleRequest request) {
        return ResponseEntity.ok(ApiResponse.success(roleService.update(id, request)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RoleResponse>> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.success(roleService.getById(id)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<RoleResponse>>> getByUser(
            @RequestParam Integer userId) {
        return ResponseEntity.ok(ApiResponse.success(roleService.getByUserId(userId)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer id) {
        roleService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Role deleted", null));
    }
}