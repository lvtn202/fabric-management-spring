package com.example.lvtn.dto;

import com.example.lvtn.dom.Debt;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class DebtDTO {
    private Long id;

    private Long type;

    private Long idTransaction;

    private String amount;

    private String createDate;

    private String total;

    public DebtDTO() {
    }

    public DebtDTO(Long id, Long type, Long idTransaction, String amount, String createDate, String total) {
        this.id = id;
        this.type = type;
        this.idTransaction = idTransaction;
        this.amount = amount;
        this.createDate = createDate;
        this.total = total;
    }

    @Override
    public String toString() {
        return "DebtDTO{" +
                "id=" + id +
                ", type=" + type +
                ", idTransaction=" + idTransaction +
                ", amount='" + amount + '\'' +
                ", createDate='" + createDate + '\'' +
                ", total='" + total + '\'' +
                '}';
    }

    static public DebtDTO convertDebtToDebtDTO(Debt debt){
        return new DebtDTO(
                debt.getId(),
                debt.getType(),
                debt.getIdTransaction(),
                String.format("%.1f", debt.getAmount()),
                String.format("%tQ", debt.getCreateDate()),
                String.format("%.1f", debt.getTotal())
        );
    }
}
