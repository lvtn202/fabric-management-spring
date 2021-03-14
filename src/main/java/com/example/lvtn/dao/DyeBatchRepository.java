package com.example.lvtn.dao;

import com.example.lvtn.dom.DyeBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DyeBatchRepository extends JpaRepository<DyeBatch, Long> {
    @Query("select db from DyeBatch db")
    List<DyeBatch> findAll();
}
