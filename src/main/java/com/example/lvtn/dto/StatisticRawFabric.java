package com.example.lvtn.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatisticRawFabric {
    private String fabricType;

    private Long rawNumber;

    private String rawLength;

    public StatisticRawFabric(String fabricType, Long rawNumber, String rawLength) {
        this.fabricType = fabricType;
        this.rawNumber = rawNumber;
        this.rawLength = rawLength;
    }

    @Override
    public String toString() {
        return "StatisticRawFabric{" +
                "fabricType='" + fabricType + '\'' +
                ", rawNumber=" + rawNumber +
                ", rawLength='" + rawLength + '\'' +
                '}';
    }
}
