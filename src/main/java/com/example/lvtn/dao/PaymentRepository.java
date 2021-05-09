package com.example.lvtn.dao;

import com.example.lvtn.dao.custom.PaymentRepositoryCustom;
import com.example.lvtn.dom.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long>, PaymentRepositoryCustom {
    @Query("select p from Payment p order by p.createDate desc ")
    List<Payment> findAll();
}
