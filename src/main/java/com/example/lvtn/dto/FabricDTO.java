package com.example.lvtn.dto;

import com.example.lvtn.dom.Fabric;
import com.example.lvtn.utils.FabricStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FabricDTO {
    private Long id;

    private Double rawLength;

    private  Double finishedLength;

    private FabricStatus status;

    private String colorName;

    private String fabricType;

    public FabricDTO() {
    }

    public FabricDTO(Long id, Double rawLength, Double finishedLength, FabricStatus status, String colorName, String fabricType) {
        this.id = id;
        this.rawLength = rawLength;
        this.finishedLength = finishedLength;
        this.status = status;
        this.colorName = colorName;
        this.fabricType = fabricType;
    }

    @Override
    public String toString() {
        return "FabricDTO{" +
                "id=" + id +
                ", rawLength=" + rawLength +
                ", finishedLength=" + finishedLength +
                ", status=" + status +
                ", colorName='" + colorName + '\'' +
                ", fabricType='" + fabricType + '\'' +
                '}';
    }

    static public FabricDTO convertFabricToFabricDTO(Fabric fabric){
        return new FabricDTO(
                fabric.getId(),
                fabric.getRawLength(),
                fabric.getFinishedLength(),
                fabric.getStatus(),
                fabric.getColorName(),
                fabric.getFabricType().getType()
        );
    }
}
