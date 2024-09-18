package com.example.BackEndSem4.repositories;

import com.example.BackEndSem4.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findRoleByName(String name);
}
