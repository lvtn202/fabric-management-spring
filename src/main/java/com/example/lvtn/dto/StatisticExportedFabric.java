package com.example.lvtn.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatisticExportedFabric {
    private String dyehouseName;

    private Long rawNumber;

    private String rawLength;

    public StatisticExportedFabric(String dyehouseName, Long rawNumber, String rawLength) {
        this.dyehouseName = dyehouseName;
        this.rawNumber = rawNumber;
        this.rawLength = rawLength;
    }

    @Override
    public String toString() {
        return "StatisticExportedFabric{" +
                "dyehouseName='" + dyehouseName + '\'' +
                ", rawNumber=" + rawNumber +
                ", rawLength='" + rawLength + '\'' +
                '}';
    }
}
