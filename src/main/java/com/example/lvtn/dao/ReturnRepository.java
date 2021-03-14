package com.example.lvtn.dao;

import com.example.lvtn.dom.Return;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReturnRepository extends JpaRepository<Return, Long> {
    @Query("select r from Return r")
    List<Return> findAll();
}
