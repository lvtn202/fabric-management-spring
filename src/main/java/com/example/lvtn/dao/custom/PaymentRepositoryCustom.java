package com.example.lvtn.dao.custom;

import com.example.lvtn.dom.Payment;

import java.util.List;

public interface PaymentRepositoryCustom {
    List<Payment> findPaymentsWithPaging(Long pageIndex, Long pageSize);

    List<Payment> findPaymentsByDyehouseIdWithPaging(Long dyehouseId, Long pageIndex, Long pageSize);
}
