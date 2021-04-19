package com.example.lvtn.dao;

import com.example.lvtn.dao.custom.DyehouseRepositoryCustom;
import com.example.lvtn.dom.Dyehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DyehouseRepository extends JpaRepository<Dyehouse, Long>, DyehouseRepositoryCustom {
    @Query("select d from Dyehouse d order by d.id")
    List<Dyehouse> findAll();
}
