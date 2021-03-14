package com.example.lvtn.dao;

import com.example.lvtn.dom.FabricType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FabricTypeRepository extends JpaRepository<FabricType, Long> {
    @Query("SELECT ft FROM FabricType ft")
    List<FabricType> findAll();
}