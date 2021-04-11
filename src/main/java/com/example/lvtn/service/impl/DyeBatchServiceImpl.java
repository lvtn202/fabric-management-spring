package com.example.lvtn.service.impl;

import com.example.lvtn.dao.DyeBatchRepository;
import com.example.lvtn.dom.DyeBatch;
import com.example.lvtn.dto.DyeBatchDTO;
import com.example.lvtn.service.DyeBatchService;
import com.example.lvtn.utils.InternalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DyeBatchServiceImpl implements DyeBatchService {
    @Autowired
    DyeBatchRepository dyeBatchRepository;

    @Override
    public List<DyeBatch> findAll() {
        return dyeBatchRepository.findAll();
    }

    @Override
    public List findDyeBatches(Long importSlipId, Long pageIndex, Long pageSize) throws InternalException {
        try {
            List<DyeBatch> listDyeBatch;
            List<DyeBatchDTO> listDyeBatchDTO = new ArrayList<DyeBatchDTO>();
            if (importSlipId < 0){
                listDyeBatch = dyeBatchRepository.findDyeBatchesWithPaging(pageIndex, pageSize);
            } else {
                listDyeBatch = dyeBatchRepository.findDyeBatchesByImportSlipIdWithPaging(importSlipId, pageIndex, pageSize);
            }

            for (DyeBatch dyeBatch: listDyeBatch){
                listDyeBatchDTO.add(DyeBatchDTO.convertDyeBatchToDyeBatchDTO(dyeBatch));
            }
            return listDyeBatchDTO;
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalException(e.getMessage());
        }
    }
}
