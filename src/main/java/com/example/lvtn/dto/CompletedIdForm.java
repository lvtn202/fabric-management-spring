package com.example.lvtn.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompletedIdForm {
    private Long orderId;

    public CompletedIdForm() {
    }

    public CompletedIdForm(Long orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "CompletedIdForm{" +
                "orderId=" + orderId +
                '}';
    }
}
