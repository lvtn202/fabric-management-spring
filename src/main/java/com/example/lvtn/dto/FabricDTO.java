package com.example.lvtn.dto;

import com.example.lvtn.dom.Fabric;
import com.example.lvtn.utils.FabricStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FabricDTO {
    private Long id;

    private String rawLength;

    private  String finishedLength;

    private FabricStatus status;

    private String colorName;

    private String fabricType;

    private String price;

    public FabricDTO() {
    }

    public FabricDTO(Long id, String rawLength, String finishedLength, FabricStatus status, String colorName, String fabricType, String price) {
        this.id = id;
        this.rawLength = rawLength;
        this.finishedLength = finishedLength;
        this.status = status;
        this.colorName = colorName;
        this.fabricType = fabricType;
        this.price = price;
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
                ", price='" + price + '\'' +
                '}';
    }

    static public FabricDTO convertFabricToFabricDTO(Fabric fabric){
        String price = (fabric.getColor() != null) ? String.format("%.3f", fabric.getColor().getPrice()) : "0.0";
        return new FabricDTO(
                fabric.getId(),
                String.format("%.1f", fabric.getRawLength()),
                String.format("%.1f", fabric.getFinishedLength()),
                fabric.getStatus(),
                fabric.getColorName(),
                fabric.getFabricType().getType(),
                price
        );
    }
}
