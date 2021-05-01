package com.example.lvtn.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
public class CreateReturnSlipForm {
    private Long userId;

    private Long dyehouseId;

    private String receivedName;

    private Timestamp returnDate;

    private List<FabricCreateReturnSlip> fabrics;

    public CreateReturnSlipForm() {
    }

    public CreateReturnSlipForm(Long userId, Long dyehouseId, String receivedName, Timestamp returnDate, List<FabricCreateReturnSlip> fabrics) {
        this.userId = userId;
        this.dyehouseId = dyehouseId;
        this.receivedName = receivedName;
        this.returnDate = returnDate;
        this.fabrics = fabrics;
    }

    @Override
    public String toString() {
        return "CreateReturnSlipForm{" +
                "userId=" + userId +
                ", dyehouseId=" + dyehouseId +
                ", receivedName='" + receivedName + '\'' +
                ", returnDate=" + returnDate +
                ", fabrics=" + fabrics +
                '}';
    }
}
