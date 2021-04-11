package com.example.lvtn.dto;

import com.example.lvtn.dom.DyeBatch;
import com.example.lvtn.dom.Fabric;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DyeBatchDTO {
    private Long id;

    private String fabricType;

    private String color;

    private String fabricLength;

    private String dyeDate;

    public DyeBatchDTO(Long id, String fabricType, String color, String fabricLength, String dyeDate) {
        this.id = id;
        this.fabricType = fabricType;
        this.color = color;
        this.fabricLength = fabricLength;
        this.dyeDate = dyeDate;
    }

    @Override
    public String toString() {
        return "DyeBatchDTO{" +
                "id=" + id +
                ", fabricType='" + fabricType + '\'' +
                ", color='" + color + '\'' +
                ", fabricLength='" + fabricLength + '\'' +
                ", dyeDate='" + dyeDate + '\'' +
                '}';
    }

    static public DyeBatchDTO convertDyeBatchToDyeBatchDTO(DyeBatch dyeBatch){
        Double fabricLength = 0.0;
        for (Fabric fabric: dyeBatch.getFabrics()){
            fabricLength += fabric.getFinishedLength();
        }
        return new DyeBatchDTO(
                dyeBatch.getId(),
                dyeBatch.getColor().getFabricType().getType(),
                dyeBatch.getColor().getName(),
                String.format("%.1f",fabricLength),
                String.format("%tQ", dyeBatch.getDyeDate()));
    }
}
