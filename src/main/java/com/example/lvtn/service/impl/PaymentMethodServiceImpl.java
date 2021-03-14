package com.example.lvtn.service.impl;

import com.example.lvtn.dao.PaymentMethodRepository;
import com.example.lvtn.dom.PaymentMethod;
import com.example.lvtn.service.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentMethodServiceImpl implements PaymentMethodService {
    @Autowired
    PaymentMethodRepository paymentMethodRepository;

    @Override
    public List<PaymentMethod> findAll() {
        return paymentMethodRepository.findAll();
    }
}
