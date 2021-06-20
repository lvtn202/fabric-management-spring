package com.example.lvtn.service;

import com.example.lvtn.dom.Order;
import com.example.lvtn.dto.CreateOrderForm;
import com.example.lvtn.dto.OrderDTO;
import com.example.lvtn.utils.InternalException;
import org.springframework.ui.ModelMap;

import java.util.List;

public interface OrderService {
    List<Order> findAll();

    List<OrderDTO> findOrderDTOsByDyehouseIdWithPaging(Long dyehouseId, Long pageIndex, Long pageSize) throws InternalException;

    ModelMap findDetailOrderById(Long id) throws InternalException;

    ModelMap createOrder(CreateOrderForm createOrderForm) throws InternalException;

    List<OrderDTO> findOrderDTOsByFabricTypeAndColor(Long dyehouseId, String fabricType, String color) throws InternalException;

    OrderDTO completeOrder(Long orderId) throws InternalException;
}
