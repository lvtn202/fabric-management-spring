package com.example.lvtn.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
public class CreateImportSlipForm {
    private Long userId;

    private Long dyehouseId;

    private  Long orderId;

    private String fabricType;

    private String color;

    private Timestamp createDate;

    private Double totalPrice;

    private List<FabricCreateImportSlip> fabrics;

    public CreateImportSlipForm() {
    }

    public CreateImportSlipForm(Long userId, Long dyehouseId, Long orderId, String fabricType, String color, Timestamp createDate, Double totalPrice, List<FabricCreateImportSlip> fabrics) {
        this.userId = userId;
        this.dyehouseId = dyehouseId;
        this.orderId = orderId;
        this.fabricType = fabricType;
        this.color = color;
        this.createDate = createDate;
        this.totalPrice = totalPrice;
        this.fabrics = fabrics;
    }

    @Override
    public String toString() {
        return "CreateImportSlipForm{" +
                "userId=" + userId +
                ", dyehouseId=" + dyehouseId +
                ", orderId=" + orderId +
                ", fabricType='" + fabricType + '\'' +
                ", color='" + color + '\'' +
                ", createDate=" + createDate +
                ", totalPrice=" + totalPrice +
                ", fabrics=" + fabrics +
                '}';
    }
}
