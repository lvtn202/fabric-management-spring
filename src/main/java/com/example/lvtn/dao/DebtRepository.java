package com.example.lvtn.dao;

import com.example.lvtn.dao.custom.DebtRepositoryCustom;
import com.example.lvtn.dom.Debt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DebtRepository extends JpaRepository<Debt, Long>, DebtRepositoryCustom {
    @Query("select d from Debt d order by d.createDate asc ")
    List<Debt> findAll();
}
