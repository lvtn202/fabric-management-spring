package com.example.lvtn.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class CreateOrderForm {
    private Long userId;

    private Long dyehouseId;

    private String fabricType;

    private String color;

    private Double orderLength;

    private Timestamp createDate;

    public CreateOrderForm() {
    }

    public CreateOrderForm(Long userId, Long dyehouseId, String fabricType, String color, Double orderLength, Timestamp createDate) {
        this.userId = userId;
        this.dyehouseId = dyehouseId;
        this.fabricType = fabricType;
        this.color = color;
        this.orderLength = orderLength;
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "CreateOrderForm{" +
                "userId=" + userId +
                ", dyehouseId=" + dyehouseId +
                ", fabricType='" + fabricType + '\'' +
                ", color='" + color + '\'' +
                ", orderLength='" + orderLength + '\'' +
                ", createDate=" + createDate +
                '}';
    }
}
