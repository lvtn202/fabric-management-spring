package com.example.lvtn.dao;

import com.example.lvtn.dom.ReturnSlip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReturnSlipRepository extends JpaRepository<ReturnSlip, Long> {
    @Query("select rs from ReturnSlip rs")
    List<ReturnSlip> findAll();
}
