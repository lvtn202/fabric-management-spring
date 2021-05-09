package com.example.lvtn.dto;

import com.example.lvtn.dom.DyeBatch;
import com.example.lvtn.dom.Fabric;
import com.example.lvtn.dom.ImportSlip;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ImportSlipDTO {
    private Long id;

    private String money;

    private String fabricNumber;

    private String fabricLength;

    private String driver;

    private String createDate;

    private String employee;

    private List<DyeBatchDTO> dyeBatchs;

    public ImportSlipDTO(Long id, String money, String fabricNumber, String fabricLength, String driver, String createDate, String employee, List<DyeBatchDTO> dyeBatchs) {
        this.id = id;
        this.money = money;
        this.fabricNumber = fabricNumber;
        this.fabricLength = fabricLength;
        this.driver = driver;
        this.createDate = createDate;
        this.employee = employee;
        this.dyeBatchs = dyeBatchs;
    }

    @Override
    public String toString() {
        return "ImportSlipDTO{" +
                "id=" + id +
                ", money='" + money + '\'' +
                ", fabricNumber='" + fabricNumber + '\'' +
                ", fabricLength='" + fabricLength + '\'' +
                ", driver='" + driver + '\'' +
                ", createDate='" + createDate + '\'' +
                ", employee='" + employee + '\'' +
                '}';
    }

    static public ImportSlipDTO convertImportSlipToImportSlipDTO(ImportSlip importSlip){
        Double fabricLength = 0.0;
        List<DyeBatchDTO> dyeBatchs = new ArrayList<>();
        for (DyeBatch dyeBatch: importSlip.getDyeBatches()){
            dyeBatchs.add(DyeBatchDTO.convertDyeBatchToDyeBatchDTO(dyeBatch));
            for (Fabric fabric: dyeBatch.getFabrics()){
                fabricLength += fabric.getFinishedLength();
            }
        }
        return new ImportSlipDTO(
                importSlip.getId(),
                String.format("%.1f", importSlip.getMoney()),
                importSlip.getFabricNumber().toString(),
                String.format("%.1f", fabricLength),
                importSlip.getDriver(),
                String.format("%tQ", importSlip.getCreateDate()),
                importSlip.getUser().getLastName(),
                dyeBatchs);
    }
}
