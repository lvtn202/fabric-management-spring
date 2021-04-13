package com.example.lvtn.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatisticFabric {
    private String fabricType;

    private String rawLength;

    private String doneLength;

    public StatisticFabric(String fabricType, String rawLength, String doneLength) {
        this.fabricType = fabricType;
        this.rawLength = rawLength;
        this.doneLength = doneLength;
    }

    @Override
    public String toString() {
        return "StatisticFabric{" +
                "fabricType='" + fabricType + '\'' +
                ", rawLength='" + rawLength + '\'' +
                ", doneLength='" + doneLength + '\'' +
                '}';
    }
}
