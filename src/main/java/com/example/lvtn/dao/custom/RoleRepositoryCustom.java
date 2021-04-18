package com.example.lvtn.dao.custom;

import com.example.lvtn.dom.Role;

import java.util.List;

public interface RoleRepositoryCustom {
    Role findRoleByName(String roleName);
}
