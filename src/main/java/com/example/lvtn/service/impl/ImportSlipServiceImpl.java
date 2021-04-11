package com.example.lvtn.service.impl;

import com.example.lvtn.dao.ImportSlipRepository;
import com.example.lvtn.dom.ImportSlip;
import com.example.lvtn.dto.ImportSlipDTO;
import com.example.lvtn.service.ImportSlipService;
import com.example.lvtn.utils.InternalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImportSlipServiceImpl implements ImportSlipService {
    @Autowired
    private ImportSlipRepository importSlipRepository;

    @Override
    public List<ImportSlip> findAll() {
        return importSlipRepository.findAll();
    }

    @Override
    public List<ImportSlipDTO> findImportSlipDTOsByOrderIdWithPaging(Long orderId, Long pageIndex, Long pageSize) throws InternalException {
        try {
            List<ImportSlip> listImportSlip = importSlipRepository.findImportSlipsByOrderIdWithPaging(orderId, pageIndex, pageSize);
            List<ImportSlipDTO> listImportSlipDTO = new ArrayList<ImportSlipDTO>();
            for (ImportSlip importSlip: listImportSlip){
                listImportSlipDTO.add(ImportSlipDTO.convertImportSlipToImportSlipDTO(importSlip));
            }
            return listImportSlipDTO;
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalException(e.getMessage());
        }
    }
}
