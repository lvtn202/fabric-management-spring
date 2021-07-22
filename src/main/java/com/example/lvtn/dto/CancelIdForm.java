package com.example.lvtn.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CancelIdForm {
    private Long orderId;

    public CancelIdForm() {
    }

    public CancelIdForm(Long orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "CancelIdForm{" +
                "orderId=" + orderId +
                '}';
    }
}
