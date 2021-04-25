package com.example.lvtn.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
public class CreateExportSlipForm {
    private Long userId;

    private Long dyehouseId;

    private String fabricType;

    private Timestamp createDate;

    private List<Long> listRaw;

    public CreateExportSlipForm() {
    }

    public CreateExportSlipForm(Long userId, Long dyehouseId, String fabricType, Timestamp createDate, List<Long> listRaw) {
        this.userId = userId;
        this.dyehouseId = dyehouseId;
        this.fabricType = fabricType;
        this.createDate = createDate;
        this.listRaw = listRaw;
    }

    @Override
    public String toString() {
        return "CreateExportSlipForm{" +
                "userId=" + userId +
                ", dyehouseId=" + dyehouseId +
                ", fabricType='" + fabricType + '\'' +
                ", createDate=" + createDate +
                ", listRaw=" + listRaw +
                '}';
    }
}
