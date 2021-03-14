package com.example.lvtn.dao;

import com.example.lvtn.dom.Dyehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DyehouseRepository extends JpaRepository<Dyehouse, Long> {
    @Query("select d from Dyehouse d")
    List<Dyehouse> findAll();
}
