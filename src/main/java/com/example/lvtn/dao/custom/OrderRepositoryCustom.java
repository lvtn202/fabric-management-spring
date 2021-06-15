package com.example.lvtn.dao.custom;

import com.example.lvtn.dom.Order;
import com.example.lvtn.utils.InternalException;

import java.util.List;

public interface OrderRepositoryCustom {

    List<Order> findOrders();

    List<Order> findOrdersByDyehouseId(Long dyehouseId);

    Order findOrderById(Long id) throws InternalException;

    List<Order> findOrdersOfDyehouseByFabricTypeAndColor(Long dyehouseId, String fabricType, String color);
}
