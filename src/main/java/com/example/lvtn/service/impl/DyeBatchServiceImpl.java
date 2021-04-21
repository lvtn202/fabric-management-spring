package com.example.lvtn.service.impl;

import com.example.lvtn.dao.DyeBatchRepository;
import com.example.lvtn.dom.DyeBatch;
import com.example.lvtn.dto.DyeBatchDTO;
import com.example.lvtn.service.DyeBatchService;
import com.example.lvtn.utils.InternalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

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

    @Override
    public ModelMap findDetailDyeBatchById(Long dyeBatchId) throws InternalException {
        try {
            DyeBatch dyeBatch = dyeBatchRepository.findDyeBatchById(dyeBatchId);

            ModelMap modelMap = new ModelMap();
            modelMap.addAttribute("dyeBatchId", dyeBatch.getId());
            modelMap.addAttribute("color", dyeBatch.getColor().getName());
            modelMap.addAttribute("dyehouseName", dyeBatch.getDyehouse().getName());
            modelMap.addAttribute("fabricType", dyeBatch.getFabrics().iterator().next().getFabricType().getType());
            modelMap.addAttribute("price", String.format("%.3f", dyeBatch.getColor().getPrice()));
            modelMap.addAttribute("dyeDate", String.format("%tQ", dyeBatch.getDyeDate()));
            modelMap.addAttribute("firstName", dyeBatch.getImportSlip().getUser().getFirstName());
            modelMap.addAttribute("lastName", dyeBatch.getImportSlip().getUser().getLastName());
            return modelMap;
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalException(e.getMessage());
        }
    }
}
