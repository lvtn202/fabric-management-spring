package com.example.lvtn.dao.custom;

import com.example.lvtn.dom.Order;

import java.util.List;

public interface OrderRepositoryCustom {
    List<Order> findOrdersByDyehouseId(Long dyehouseId);

    List<Order> findOrdersWithPaging(Long pageIndex, Long pageSize);

    List<Order> findOrdersByDyehouseIdWithPaging(Long dyehouseId, Long pageIndex, Long pageSize);

    Order findOrderById(Long id);
}
