package com.example.lvtn.service;

import com.example.lvtn.dom.ExportSlip;
import com.example.lvtn.dto.CreateExportSlipForm;
import com.example.lvtn.utils.InternalException;
import org.springframework.ui.ModelMap;

import java.util.List;

public interface ExportSlipService {
    List<ExportSlip> findAll();

    ModelMap createExportSlip(CreateExportSlipForm createExportSlipForm) throws InternalException;
}
