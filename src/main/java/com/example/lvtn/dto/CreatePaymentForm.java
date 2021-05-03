package com.example.lvtn.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class CreatePaymentForm {
    private Long userId;

    private Long dyehouseId;

    private Long paymentMethodId;

    private Double money;

    private Timestamp createDate;

    private String bankName;

    private String recipientName;

    public CreatePaymentForm() {
    }

    public CreatePaymentForm(Long userId, Long dyehouseId, Long paymentMethodId, Double money, Timestamp createDate, String bankName, String recipientName) {
        this.userId = userId;
        this.dyehouseId = dyehouseId;
        this.paymentMethodId = paymentMethodId;
        this.money = money;
        this.createDate = createDate;
        this.bankName = bankName;
        this.recipientName = recipientName;
    }

    @Override
    public String toString() {
        return "CreatePaymentForm{" +
                "userId=" + userId +
                ", dyehouseId=" + dyehouseId +
                ", paymentMethodId=" + paymentMethodId +
                ", money=" + money +
                ", createDate=" + createDate +
                ", bankName='" + bankName + '\'' +
                ", recipientName='" + recipientName + '\'' +
                '}';
    }
}
