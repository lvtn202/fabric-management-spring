package com.example.lvtn.service;

import com.example.lvtn.dom.PaymentMethod;
import com.example.lvtn.dto.PaymentMethodDTO;
import com.example.lvtn.utils.InternalException;

import java.util.List;

public interface PaymentMethodService {
    List<PaymentMethod> findAll();

    List<PaymentMethodDTO> findPaymentMethodDTOs() throws InternalException;
}
