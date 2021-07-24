package com.example.lvtn.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class DebtForm {
    private Long dyehouseId;

    private Timestamp startDate;

    private Timestamp endDate;

    public DebtForm() {
    }

    public DebtForm(Long dyehouseId, Timestamp startDate, Timestamp endDate) {
        this.dyehouseId = dyehouseId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "DebtForm{" +
                "dyehouseId=" + dyehouseId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
