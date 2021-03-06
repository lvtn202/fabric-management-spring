package com.example.lvtn.dao;

import com.example.lvtn.dao.custom.DyeBatchRepositoryCustom;
import com.example.lvtn.dom.DyeBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DyeBatchRepository extends JpaRepository<DyeBatch, Long>, DyeBatchRepositoryCustom {
    @Query("select db from DyeBatch db order by db.dyeDate desc ")
    List<DyeBatch> findAll();
}
