package com.example.lvtn.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FabricCreateImportSlip {
    private Long fabricId;

    private Double finishedLength;

    public FabricCreateImportSlip() {
    }

    public FabricCreateImportSlip(Long fabricId, Double finishedLength) {
        this.fabricId = fabricId;
        this.finishedLength = finishedLength;
    }

    @Override
    public String toString() {
        return "FabricCreateImportSlip{" +
                "fabricId=" + fabricId +
                ", finishedLength=" + finishedLength +
                '}';
    }
}
