package com.example.lvtn.dao;

import com.example.lvtn.dom.Fabric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FabricRepository extends JpaRepository<Fabric, Long> {
    @Query("select f from Fabric f")
    List<Fabric> findAll();
}
