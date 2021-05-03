package com.example.lvtn.dto;

import com.example.lvtn.dom.PaymentMethod;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentMethodDTO {
    private Long paymentMethodId;

    private String name;

    public PaymentMethodDTO() {
    }

    public PaymentMethodDTO(Long paymentMethodId, String name) {
        this.paymentMethodId = paymentMethodId;
        this.name = name;
    }

    @Override
    public String toString() {
        return "PaymentMethodDTO{" +
                "paymentMethodId=" + paymentMethodId +
                ", name='" + name + '\'' +
                '}';
    }

    static public PaymentMethodDTO convertPaymentMethodToPaymentMethodDTO(PaymentMethod paymentMethod){
        return new PaymentMethodDTO(
                paymentMethod.getId(),
                paymentMethod.getName());
    }
}
