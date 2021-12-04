package com.lhind.tripapplication.service;

import com.lhind.tripapplication.entity.Role;

public interface RoleService {
    Role save(Role role);

    Role findByName(String name);
}
