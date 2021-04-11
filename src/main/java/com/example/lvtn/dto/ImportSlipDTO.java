package com.example.lvtn.dto;

import com.example.lvtn.dom.DyeBatch;
import com.example.lvtn.dom.Fabric;
import com.example.lvtn.dom.ImportSlip;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImportSlipDTO {
    private Long id;

    private String money;

    private String fabricNumber;

    private String fabricLength;

    private String createDate;

    private String employee;

    public ImportSlipDTO(Long id, String money, String fabricNumber, String fabricLength, String createDate, String employee) {
        this.id = id;
        this.money = money;
        this.fabricNumber = fabricNumber;
        this.fabricLength = fabricLength;
        this.createDate = createDate;
        this.employee = employee;
    }

    @Override
    public String toString() {
        return "ImportSlipDTO{" +
                "id=" + id +
                ", money='" + money + '\'' +
                ", fabricNumber='" + fabricNumber + '\'' +
                ", fabricLength='" + fabricLength + '\'' +
                ", createDate='" + createDate + '\'' +
                ", employee='" + employee + '\'' +
                '}';
    }

    static public ImportSlipDTO convertImportSlipToImportSlipDTO(ImportSlip importSlip){
        Double fabricLength = 0.0;
        for (DyeBatch dyeBatch: importSlip.getDyeBatches()){
            for (Fabric fabric: dyeBatch.getFabrics()){
                fabricLength += fabric.getFinishedLength();
            }
        }
        return new ImportSlipDTO(
                importSlip.getId(),
                String.format("%.1f", importSlip.getMoney()),
                importSlip.getFabricNumber().toString(),
                String.format("%.1f", fabricLength),
                String.format("%tQ", importSlip.getCreateDate()),
                importSlip.getEmployee().getLastName());
    }
}
