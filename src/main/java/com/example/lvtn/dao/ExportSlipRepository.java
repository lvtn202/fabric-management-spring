package com.example.lvtn.dao;

import com.example.lvtn.dao.custom.ExportSlipRepositoryCustom;
import com.example.lvtn.dom.ExportSlip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExportSlipRepository extends JpaRepository<ExportSlip, Long>, ExportSlipRepositoryCustom {
    @Query("select e from ExportSlip e order by e.createDate desc ")
    List<ExportSlip> findAll();
}
