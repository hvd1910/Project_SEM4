package com.example.BackEndSem4.controllers;

import com.example.BackEndSem4.models.Role;
import com.example.BackEndSem4.response.Response;
import com.example.BackEndSem4.services.role.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @GetMapping("")
    public ResponseEntity<?> getAllRoles() {
        List<Role> roles = roleService.getAllRoles();
        return ResponseEntity.ok(new Response("success", "Get roles successfully.", roles));
    }

}
