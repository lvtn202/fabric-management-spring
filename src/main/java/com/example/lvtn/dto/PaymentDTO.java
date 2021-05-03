package com.example.lvtn.dto;

import com.example.lvtn.dom.Payment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentDTO {
    private Long paymentId;

    private String money;

    private String createDate;

    private String bankName;

    private String recipientName;

    private String paymentMethod;

    private String dyehouseName;

    private String userFirstName;

    private String userLastName;

    public PaymentDTO() {
    }

    public PaymentDTO(Long paymentId, String money, String createDate, String bankName, String recipientName, String paymentMethod, String dyehouseName, String userFirstName, String userLastName) {
        this.paymentId = paymentId;
        this.money = money;
        this.createDate = createDate;
        this.bankName = bankName;
        this.recipientName = recipientName;
        this.paymentMethod = paymentMethod;
        this.dyehouseName = dyehouseName;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
    }

    @Override
    public String toString() {
        return "PaymentDTO{" +
                "paymentId=" + paymentId +
                ", money='" + money + '\'' +
                ", createDate='" + createDate + '\'' +
                ", bankName='" + bankName + '\'' +
                ", recipientName='" + recipientName + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", dyehouseName='" + dyehouseName + '\'' +
                ", userFirstName='" + userFirstName + '\'' +
                ", userLastName='" + userLastName + '\'' +
                '}';
    }

    static public PaymentDTO convertPaymentToPaymentDTO(Payment payment){
        return new PaymentDTO(
                payment.getId(),
                String.format("%.3f", payment.getMoney()),
                String.format("%tQ", payment.getCreateDate()),
                payment.getBankName(),
                payment.getRecipientName(),
                payment.getPaymentMethod().getName(),
                payment.getDyehouse().getName(),
                payment.getUser().getFirstName(),
                payment.getUser().getLastName()
        );
    }
}
