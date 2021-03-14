package com.example.lvtn.dao;

import com.example.lvtn.dom.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {
    @Query("select pm from PaymentMethod pm")
    List<PaymentMethod> findAll();
}
