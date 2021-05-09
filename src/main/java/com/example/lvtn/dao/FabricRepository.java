package com.example.lvtn.dao;

import com.example.lvtn.dao.custom.FabricRepositoryCustom;
import com.example.lvtn.dom.Fabric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FabricRepository extends JpaRepository<Fabric, Long>, FabricRepositoryCustom {
    @Query("select f from Fabric f order by f.id")
    List<Fabric> findAll();
}
