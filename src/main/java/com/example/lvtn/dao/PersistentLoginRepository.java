package com.example.lvtn.dao;

import com.example.lvtn.dom.PersistentLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersistentLoginRepository extends JpaRepository<PersistentLogin, Long> {
    @Query("select pl from PersistentLogin pl")
    List<PersistentLogin> findAll();
}
