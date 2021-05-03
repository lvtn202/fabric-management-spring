package com.example.lvtn.service.impl;

import com.example.lvtn.dao.PaymentMethodRepository;
import com.example.lvtn.dom.PaymentMethod;
import com.example.lvtn.dto.PaymentMethodDTO;
import com.example.lvtn.service.PaymentMethodService;
import com.example.lvtn.utils.InternalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentMethodServiceImpl implements PaymentMethodService {
    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Override
    public List<PaymentMethod> findAll() {
        return paymentMethodRepository.findAll();
    }

    @Override
    public List<PaymentMethodDTO> findPaymentMethodDTOs() throws InternalException {
        try {
            List<PaymentMethod> listPaymentMethod = paymentMethodRepository.findAll();
            List<PaymentMethodDTO> listPaymentMethodDTO = new ArrayList<>();
            for (PaymentMethod paymentMethod: listPaymentMethod){
                listPaymentMethodDTO.add(PaymentMethodDTO.convertPaymentMethodToPaymentMethodDTO(paymentMethod));
            }
            return listPaymentMethodDTO;
        } catch (Exception e){
            e.printStackTrace();
            throw new InternalException(e.getMessage());
        }
    }
}
