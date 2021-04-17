package com.example.lvtn.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatisticCompletedFabric {
    private String fabricType;

    private Long doneNumber;

    private String doneLength;

    public StatisticCompletedFabric(String fabricType, Long doneNumber, String doneLength) {
        this.fabricType = fabricType;
        this.doneNumber = doneNumber;
        this.doneLength = doneLength;
    }

    @Override
    public String toString() {
        return "StatisticFabric{" +
                "fabricType='" + fabricType + '\'' +
                ", doneNumber='" + doneNumber + '\'' +
                ", doneLength='" + doneLength + '\'' +
                '}';
    }
}
