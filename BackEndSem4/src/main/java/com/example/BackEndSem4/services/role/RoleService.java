package com.example.BackEndSem4.services.role;

import com.example.BackEndSem4.models.Role;
import com.example.BackEndSem4.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService{
    private final RoleRepository roleRepository;
    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}
