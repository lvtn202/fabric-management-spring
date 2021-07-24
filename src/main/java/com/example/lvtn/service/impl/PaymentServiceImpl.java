package com.example.lvtn.service.impl;

import com.example.lvtn.dao.DyehouseRepository;
import com.example.lvtn.dao.PaymentMethodRepository;
import com.example.lvtn.dao.PaymentRepository;
import com.example.lvtn.dao.UserRepository;
import com.example.lvtn.dom.Dyehouse;
import com.example.lvtn.dom.Payment;
import com.example.lvtn.dto.CreatePaymentForm;
import com.example.lvtn.dto.PaymentDTO;
import com.example.lvtn.service.DebtService;
import com.example.lvtn.service.PaymentService;
import com.example.lvtn.utils.InternalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private DyehouseRepository dyehouseRepository;

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DebtService debtService;

    @Override
    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }

    @Override
    public List<PaymentDTO> findPaymentDTOs(Long dyehouseId, Long pageIndex, Long pageSize) throws InternalException {
        try {
            List<Payment> listPayment = new ArrayList<>();
            List<PaymentDTO> listPaymentDTO = new ArrayList<>();
            if (dyehouseId < 0) {
                listPayment = paymentRepository.findPaymentsWithPaging(pageIndex, pageSize);
            } else {
                listPayment = paymentRepository.findPaymentsByDyehouseIdWithPaging(dyehouseId, pageIndex, pageSize);
            }

            for (Payment payment: listPayment){
                listPaymentDTO.add(PaymentDTO.convertPaymentToPaymentDTO(payment));
            }
            return listPaymentDTO;
        } catch (Exception e){
            e.printStackTrace();
            throw new InternalException(e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public PaymentDTO createPayment(CreatePaymentForm createPaymentForm) throws InternalException {
        try {
            Payment newPayment = new Payment(
                    createPaymentForm.getMoney(),
                    createPaymentForm.getCreateDate(),
                    createPaymentForm.getBankName(),
                    createPaymentForm.getRecipientName(),
                    paymentMethodRepository.getOne(createPaymentForm.getPaymentMethodId()),
                    dyehouseRepository.findDyehouseById(createPaymentForm.getDyehouseId()),
                    userRepository.findUsersById(createPaymentForm.getUserId())
            );
            Dyehouse dyehouse = dyehouseRepository.findDyehouseById(createPaymentForm.getDyehouseId());
            Double oldDebt = dyehouse.getDebt();
            dyehouse.setDebt(dyehouse.getDebt() - createPaymentForm.getMoney());
            dyehouseRepository.save(dyehouse);
            paymentRepository.save(newPayment);

            debtService.createDebt(3L, dyehouse, newPayment.getId(), createPaymentForm.getMoney(), createPaymentForm.getCreateDate(), oldDebt);

            return PaymentDTO.convertPaymentToPaymentDTO(newPayment);
        } catch (Exception e){
            e.printStackTrace();
            throw new InternalException(e.getMessage());
        }
    }

    @Override
    public String findTotalRecentPayment(Long dyehouseId, Long period) throws InternalException {
        try {
            List<Payment> listPayment = new ArrayList<>();
            Double totalPrice = 0.0;
            if(dyehouseId < 0){
                listPayment = paymentRepository.findTotalRecentPayment(period);
            } else {
                listPayment = paymentRepository.findTotalRecentPaymentInDyehouse(dyehouseId, period);
            }

            for (Payment payment: listPayment){
//                System.out.println(payment.getCreateDate() + " with: " + payment.getMoney() + " vnd" );
                totalPrice += payment.getMoney();
            }
            return String.format("%.3f", totalPrice);
        } catch (Exception e){
            e.printStackTrace();
            throw new InternalException(e.getMessage());
        }
    }
}
