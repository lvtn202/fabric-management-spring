package com.example.lvtn.dao;

import com.example.lvtn.dao.custom.ImportSlipRepositoryCustom;
import com.example.lvtn.dom.ImportSlip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImportSlipRepository extends JpaRepository<ImportSlip, Long>, ImportSlipRepositoryCustom {
    @Query("select i from ImportSlip i order by i.createDate desc ")
    List<ImportSlip> findAll();
}
