package com.example.lvtn.service;

import com.example.lvtn.dom.ImportSlip;
import com.example.lvtn.dto.ImportSlipDTO;
import com.example.lvtn.utils.InternalException;

import java.util.List;

public interface ImportSlipService {
    List<ImportSlip> findAll();

    List<ImportSlipDTO> findImportSlipDTOsByOrderIdWithPaging(Long orderId, Long pageIndex, Long pageSize) throws InternalException;
}
