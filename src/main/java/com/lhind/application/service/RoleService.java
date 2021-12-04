package com.lhind.application.service;

import com.lhind.application.entity.Role;

public interface RoleService {
    Role save(Role role);

    Role findByName(String name);
}
