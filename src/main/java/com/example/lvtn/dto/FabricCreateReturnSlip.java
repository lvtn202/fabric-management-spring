package com.example.lvtn.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FabricCreateReturnSlip {
    private Long fabricId;

    private Double returnLength;

    private String returnReason;

    private Double money;

    public FabricCreateReturnSlip() {
    }

    public FabricCreateReturnSlip(Long fabricId, Double returnLength, String returnReason, Double money) {
        this.fabricId = fabricId;
        this.returnLength = returnLength;
        this.returnReason = returnReason;
        this.money = money;
    }

    @Override
    public String toString() {
        return "FabricCreateReturnSlip{" +
                "fabricId=" + fabricId +
                ", returnLength=" + returnLength +
                ", returnReason='" + returnReason + '\'' +
                ", money=" + money +
                '}';
    }
}
