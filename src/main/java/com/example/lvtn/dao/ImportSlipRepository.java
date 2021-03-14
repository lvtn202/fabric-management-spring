package com.example.lvtn.dao;

import com.example.lvtn.dom.ImportSlip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImportSlipRepository extends JpaRepository<ImportSlip, Long> {
    @Query("select i from ImportSlip i")
    List<ImportSlip> findAll();
}
