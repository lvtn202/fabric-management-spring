package com.example.lvtn.dao;

import com.example.lvtn.dao.custom.UserRepositoryCustom;
import com.example.lvtn.dom.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
    @Query("select e from User e order by e.id")
    List<User> findAll();
}
