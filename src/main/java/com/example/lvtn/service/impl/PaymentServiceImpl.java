package com.example.lvtn.service.impl;

import com.example.lvtn.dao.PaymentRepository;
import com.example.lvtn.dom.Payment;
import com.example.lvtn.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    PaymentRepository paymentRepository;

    @Override
    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }
}
