package com.example.lvtn.dao;

import com.example.lvtn.dao.custom.RoleRepositoryCustom;
import com.example.lvtn.dom.Role;
import com.example.lvtn.dom.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>, RoleRepositoryCustom {
    @Query("select r from Role r")
    List<Role> findAll();
}
