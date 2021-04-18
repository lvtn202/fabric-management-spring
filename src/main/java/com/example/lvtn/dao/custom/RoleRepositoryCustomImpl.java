package com.example.lvtn.dao.custom;

import com.example.lvtn.dom.Role;
import com.example.lvtn.dom.User;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class RoleRepositoryCustomImpl implements RoleRepositoryCustom{
    @Autowired
    private EntityManager entityManager;

    @Override
    public Role findRoleByName(String roleName) {
        try {
            String sql = "select r from " + Role.class.getName() + " r "
                    + "where r.name = :roleName";
            Query query = entityManager.createQuery(sql, Role.class);
            query.setParameter("roleName", roleName);
            return (Role) query.getSingleResult();
        } catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }
}
