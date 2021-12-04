package com.lhind.application.utility.mapper;

import com.lhind.application.entity.Role;
import com.lhind.application.utility.model.RoleDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RoleMapper {

    public RoleDto roleToRoleDto(Role role) {
        if (role == null) {
            return null;
        }

        RoleDto roleDto = new RoleDto();
        roleDto.setName(role.getName());

        return roleDto;
    }

    public Role roleDtoToRole(RoleDto roleDto) {
        if (roleDto == null) {
            return null;
        }

        Role role = new Role();
        role.setName(roleDto.getName());

        return role;
    }

}
