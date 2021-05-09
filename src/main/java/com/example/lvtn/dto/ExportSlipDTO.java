package com.example.lvtn.dto;

import com.example.lvtn.dom.ExportSlip;
import com.example.lvtn.dom.Fabric;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExportSlipDTO {
    private Long id;

    private String fabricType;

    private String fabricNumber;

    private String fabricLength;

    private String createDate;

    private String dyehouse;

    private String user;

    public ExportSlipDTO() {
    }

    public ExportSlipDTO(Long id, String fabricType, String fabricNumber, String fabricLength, String createDate, String dyehouse, String user) {
        this.id = id;
        this.fabricType = fabricType;
        this.fabricNumber = fabricNumber;
        this.fabricLength = fabricLength;
        this.createDate = createDate;
        this.dyehouse = dyehouse;
        this.user = user;
    }

    @Override
    public String toString() {
        return "ExportSlipDTO{" +
                "id=" + id +
                ", fabricType='" + fabricType + '\'' +
                ", fabricNumber='" + fabricNumber + '\'' +
                ", fabricLength='" + fabricLength + '\'' +
                ", createDate='" + createDate + '\'' +
                ", dyehouse='" + dyehouse + '\'' +
                ", user='" + user + '\'' +
                '}';
    }

    static public ExportSlipDTO convertExportSlipToExportSlipDTO(ExportSlip exportSlip){
        Double fabricLength = 0.0;
        for (Fabric fabric: exportSlip.getFabrics()){
            fabricLength += fabric.getRawLength();
        }
        return new ExportSlipDTO(
                exportSlip.getId(),
                exportSlip.getFabrics().iterator().next().getFabricType().getType(),
                exportSlip.getFabricNumber().toString(),
                String.format("%.1f", fabricLength),
                String.format("%tQ", exportSlip.getCreateDate()),
                exportSlip.getDyehouse().getName(),
                exportSlip.getUser().getLastName()
        );
    }
}
