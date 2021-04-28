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

    private String driver;

    private Timestamp createDate;

    private List<FabricCreateImportSlip> fabrics;

    public CreateImportSlipForm() {
    }

    public CreateImportSlipForm(Long userId, Long dyehouseId, Long orderId, String fabricType, String color, String driver, Timestamp createDate, List<FabricCreateImportSlip> fabrics) {
        this.userId = userId;
        this.dyehouseId = dyehouseId;
        this.orderId = orderId;
        this.fabricType = fabricType;
        this.color = color;
        this.driver = driver;
        this.createDate = createDate;
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
                ", driver='" + driver + '\'' +
                ", createDate=" + createDate +
                ", fabrics=" + fabrics +
                '}';
    }
}
