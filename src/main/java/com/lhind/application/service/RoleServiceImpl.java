package com.lhind.application.service;

import com.lhind.application.entity.Role;
import com.lhind.application.exception.ResourceNotFoundException;
import com.lhind.application.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        Optional<Role> roleOptional = roleRepository.findByName(name);

        return getRole(roleOptional);
    }

    private Role getRole(Optional<Role> roleOptional) {
        if (roleOptional.isEmpty()) {
            throw new ResourceNotFoundException();
        }

        return roleOptional.get();
    }


}
