package com.example.lvtn.service;

import com.example.lvtn.dom.ExportSlip;
import com.example.lvtn.dto.CreateExportSlipForm;
import com.example.lvtn.dto.ExportSlipDTO;
import com.example.lvtn.utils.InternalException;
import org.springframework.ui.ModelMap;

import java.util.List;

public interface ExportSlipService {
    List<ExportSlip> findAll();

    ModelMap createExportSlip(CreateExportSlipForm createExportSlipForm) throws InternalException;

    List<ExportSlipDTO> findRecentExportSlipDTOs(Long dyehouseId, Long pageIndex, Long pageSize) throws InternalException;

    List<ModelMap> findExportSlips(Long pageIndex, Long pageSize) throws InternalException;
}
