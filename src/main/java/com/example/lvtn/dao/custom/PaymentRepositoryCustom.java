package com.example.lvtn.dao.custom;

import com.example.lvtn.dom.Payment;
import com.example.lvtn.utils.InternalException;

import java.util.List;

public interface PaymentRepositoryCustom {
    List<Payment> findPaymentsWithPaging(Long pageIndex, Long pageSize);

    List<Payment> findPaymentsByDyehouseIdWithPaging(Long dyehouseId, Long pageIndex, Long pageSize);

    List<Payment> findTotalRecentPayment(Long period);

    List<Payment> findTotalRecentPaymentInDyehouse(Long dyehouseId, Long period);

    Payment findPaymentById(Long id) throws InternalException;
}
