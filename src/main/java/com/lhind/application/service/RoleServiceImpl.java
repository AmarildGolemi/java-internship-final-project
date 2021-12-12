package com.lhind.application.service;

import com.lhind.application.entity.Role;
import com.lhind.application.exception.ResourceNotFoundException;
import com.lhind.application.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role findByName(String name) {
        Role role = roleRepository.findByName(name);

        if (role == null) {
            throw new ResourceNotFoundException();
        }

        return role;
    }

}
