package com.example.lvtn.dto;

import com.example.lvtn.dom.Return;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReturnDTO {
    private Long returnId;

    private String color;

    private String fabricType;

    private String doneLength;

    private String returnLength;

    private String returnReason;

    private String money;

    public ReturnDTO() {
    }

    public ReturnDTO(Long returnId, String color, String fabricType, String doneLength, String returnLength, String returnReason, String money) {
        this.returnId = returnId;
        this.color = color;
        this.fabricType = fabricType;
        this.doneLength = doneLength;
        this.returnLength = returnLength;
        this.returnReason = returnReason;
        this.money = money;
    }

    @Override
    public String toString() {
        return "ReturnDTO{" +
                "returnId=" + returnId +
                ", color='" + color + '\'' +
                ", fabricType='" + fabricType + '\'' +
                ", doneLength='" + doneLength + '\'' +
                ", returnLength='" + returnLength + '\'' +
                ", returnReason='" + returnReason + '\'' +
                ", money='" + money + '\'' +
                '}';
    }

    static public ReturnDTO convertReturnToReturnDTO(Return aReturn){
        return new ReturnDTO(
                aReturn.getId(),
                aReturn.getFabric().getColor().getName(),
                aReturn.getFabric().getFabricType().getType(),
                String.format("%.1f", aReturn.getFabric().getFinishedLength()),
                String.format("%.1f", aReturn.getReturnLength()),
                aReturn.getReturnReason(),
                String.format("%.3f", aReturn.getMoney())
        );
    }
}
