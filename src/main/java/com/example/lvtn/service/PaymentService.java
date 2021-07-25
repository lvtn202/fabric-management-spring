package com.example.lvtn.service;

import com.example.lvtn.dom.Payment;
import com.example.lvtn.dto.CreatePaymentForm;
import com.example.lvtn.dto.PaymentDTO;
import com.example.lvtn.utils.InternalException;
import org.springframework.ui.ModelMap;

import java.util.List;

public interface PaymentService {
    List<Payment> findAll();

    List<PaymentDTO> findPaymentDTOs(Long dyehouseId, Long pageIndex, Long pageSize) throws InternalException;

    PaymentDTO createPayment(CreatePaymentForm createPaymentForm) throws InternalException;

    String findTotalRecentPayment(Long dyehouseId, Long period) throws InternalException;

    PaymentDTO findDetailPaymentDTOByPaymentId(Long paymentId) throws InternalException;
}
