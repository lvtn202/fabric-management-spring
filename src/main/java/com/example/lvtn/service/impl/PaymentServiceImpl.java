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
import com.example.lvtn.utils.EmailSender;
import com.example.lvtn.utils.InternalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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

    @Autowired
    public EmailSender emailSender;

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

            String email = newPayment.getDyehouse().getEmail();
            String subject = "Thanh toán thành công !";
            String name = "";
            if (newPayment.getUser().getFirstName() != null){
                name += newPayment.getUser().getFirstName();
                name += " ";
            }
            name += newPayment.getUser().getLastName();
//            ZoneId asiaHCM = ZoneId.of("Asia/Ho_Chi_Minh");
//            ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(newPayment.getCreateDate().toInstant(), asiaHCM);
            Instant timestamp = newPayment.getCreateDate().toInstant();
            ZonedDateTime zonedDateTime = timestamp.atZone(ZoneId.of("Asia/Ho_Chi_Minh"));
            System.out.println(zonedDateTime);
            String content = "Xin chào,\n\n"
                    + "Thanh toán đã được tạo thành công.\n"
                    + "Mã thanh toán: " + newPayment.getId().toString() + "\n"
                    + "Số tiền thanh toán: " + String.format("%,.1f", newPayment.getMoney()) + " (vnd)\n"
                    + (newPayment.getBankName() == null ? "" : "Ngân hàng: " + newPayment.getBankName() + "\n")
                    + "Phương thức thanh toán: " + newPayment.getPaymentMethod().getName() + "\n"
                    + "Người nhận: " + newPayment.getRecipientName() + "\n"
                    + "Ngày thanh toán: " + zonedDateTime + "\n"
                    + "Người thanh toán: " + name + "\n\n"
                    + "Trân trọng !";
            emailSender.sendEmail(email, subject, content);

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

    @Override
    public PaymentDTO findDetailPaymentDTOByPaymentId(Long paymentId) throws InternalException {
        try {
            if (paymentId <= 0L){
                throw new InternalException("ERROR_ID");
            }
            Payment payment = paymentRepository.findPaymentById(paymentId);
            return PaymentDTO.convertPaymentToPaymentDTO(payment);
        } catch (Exception e){
            e.printStackTrace();
            throw new InternalException(e.getMessage());
        }
    }
}
