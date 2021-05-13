package com.example.lvtn.service.impl;

import com.example.lvtn.dao.DyehouseRepository;
import com.example.lvtn.dao.ExportSlipRepository;
import com.example.lvtn.dao.FabricRepository;
import com.example.lvtn.dao.UserRepository;
import com.example.lvtn.dom.ExportSlip;
import com.example.lvtn.dom.Fabric;
import com.example.lvtn.dom.ImportSlip;
import com.example.lvtn.dto.CreateExportSlipForm;
import com.example.lvtn.dto.ExportSlipDTO;
import com.example.lvtn.dto.ImportSlipDTO;
import com.example.lvtn.service.ExportSlipService;
import com.example.lvtn.utils.FabricStatus;
import com.example.lvtn.utils.InternalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ExportSlipServiceImpl implements ExportSlipService {
    @Autowired
    ExportSlipRepository exportSlipRepository;

    @Autowired
    FabricRepository fabricRepository;

    @Autowired
    DyehouseRepository dyehouseRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public List<ExportSlip> findAll() {
        return exportSlipRepository.findAll();
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public ModelMap createExportSlip(CreateExportSlipForm createExportSlipForm) throws InternalException {
        try {
            Set<Fabric> setFabric = new HashSet<>();
            for (Long fabricId: createExportSlipForm.getListRaw()){
                setFabric.add(fabricRepository.findFabricById(fabricId));
            }

            ExportSlip exportSlip = new ExportSlip(
                    (long) createExportSlipForm.getListRaw().size(),
                    createExportSlipForm.getCreateDate(),
                    dyehouseRepository.findDyehouseById(createExportSlipForm.getDyehouseId()),
                    userRepository.findUsersById(createExportSlipForm.getUserId()),
                    setFabric
            );
            exportSlipRepository.save(exportSlip);

            for(Fabric fabric: setFabric){
                fabric.setExportSlip(exportSlip);
                fabric.setStatus(FabricStatus.EXPORTED);
                fabric.setDyehouse(dyehouseRepository.findDyehouseById(createExportSlipForm.getDyehouseId()));
                fabricRepository.save(fabric);
            }

            ModelMap modelMap = new ModelMap();
            modelMap.addAttribute("exportSlipId", exportSlip.getId());
            modelMap.addAttribute("fabricNumber", exportSlip.getFabricNumber());
            modelMap.addAttribute("createDate", String.format("%tQ", exportSlip.getCreateDate()));
            modelMap.addAttribute("dyehouseName", exportSlip.getDyehouse().getName());
            modelMap.addAttribute("firstName", exportSlip.getUser().getFirstName());
            modelMap.addAttribute("lastName", exportSlip.getUser().getLastName());
            return modelMap;
        } catch (Exception e){
            e.printStackTrace();
            throw new InternalException(e.getMessage());
        }
    }

    @Override
    public List<ExportSlipDTO> findRecentExportSlipDTOs(Long dyehouseId, Long pageIndex, Long pageSize) throws InternalException {
        try {
            List<ExportSlip> listExportSlip = new ArrayList<>();
            List<ExportSlipDTO> listExportSlipDTO = new ArrayList<ExportSlipDTO>();
            if(dyehouseId < 0){
                listExportSlip = exportSlipRepository.findRecentExportSlips(pageIndex, pageSize);
            } else {
                listExportSlip = exportSlipRepository.findRecentExportSlipsInDyehouse(dyehouseId, pageIndex, pageSize);
            }

            for (ExportSlip exportSlip: listExportSlip){
                listExportSlipDTO.add(ExportSlipDTO.convertExportSlipToExportSlipDTO(exportSlip));
            }
            return listExportSlipDTO;
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalException(e.getMessage());
        }
    }
}
