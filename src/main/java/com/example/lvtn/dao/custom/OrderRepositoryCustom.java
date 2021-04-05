package com.example.lvtn.dao.custom;

import com.example.lvtn.dom.Order;

import java.util.List;

public interface OrderRepositoryCustom {
    List<Order> findOrdersByDyehouseId(Long dyehouseId);
}
