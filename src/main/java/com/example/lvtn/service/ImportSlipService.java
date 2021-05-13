package com.example.lvtn.service;

import com.example.lvtn.dom.ImportSlip;
import com.example.lvtn.dto.CreateImportSlipForm;
import com.example.lvtn.dto.ImportSlipDTO;
import com.example.lvtn.utils.InternalException;
import org.springframework.ui.ModelMap;

import java.util.List;

public interface ImportSlipService {
    List<ImportSlip> findAll();

    List<ImportSlipDTO> findImportSlipDTOsByOrderIdWithPaging(Long orderId, Long pageIndex, Long pageSize) throws InternalException;

    ModelMap createImportSlip(CreateImportSlipForm createImportSlipForm) throws InternalException;

    List<ImportSlipDTO> findRecentImportSlipDTOs(Long dyehouseId, Long pageIndex, Long pageSize) throws InternalException;
}
