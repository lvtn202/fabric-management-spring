package com.example.lvtn.service.impl;

import com.example.lvtn.dao.OrderRepository;
import com.example.lvtn.dom.Order;
import com.example.lvtn.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }
}
