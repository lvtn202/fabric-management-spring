package com.example.lvtn.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FabricCreateImportSlip {
    private Long id;

    private Double finishedLength;

    public FabricCreateImportSlip() {
    }

    public FabricCreateImportSlip(Long id, Double finishedLength) {
        this.id = id;
        this.finishedLength = finishedLength;
    }

    @Override
    public String toString() {
        return "FabricCreateImportSlip{" +
                "id=" + id +
                ", finishedLength=" + finishedLength +
                '}';
    }
}
