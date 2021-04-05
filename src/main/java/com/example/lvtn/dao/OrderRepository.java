package com.example.lvtn.dao;

import com.example.lvtn.dao.custom.OrderRepositoryCustom;
import com.example.lvtn.dom.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryCustom {
    @Query("select o from Order o")
    List<Order> findAll();
}
