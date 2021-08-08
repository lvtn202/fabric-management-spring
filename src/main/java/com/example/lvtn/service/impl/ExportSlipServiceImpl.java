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
import com.example.lvtn.utils.EmailSender;
import com.example.lvtn.utils.FabricStatus;
import com.example.lvtn.utils.InternalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

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

    @Autowired
    public EmailSender emailSender;

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

            String email = dyehouseRepository.findDyehouseById(createExportSlipForm.getDyehouseId()).getEmail();
            String subject = "Phiếu xuất vải mới";
            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+7"));
            Double totalLength = 0.0;
            for (Fabric fabric: exportSlip.getFabrics()){
                totalLength += fabric.getRawLength();
            }
            String content = "Xin chào xưởng " + exportSlip.getDyehouse().getName() + ",\n\n"
                    + "Phiếu xuất vải mới đã được tạo.\n"
                    + "Mã phiếu xuất: " + exportSlip.getId().toString() + "\n"
                    + "Số lượng cây vải: " + exportSlip.getFabricNumber().toString() + "\n"
                    + "Tổng độ dài: " + String.format("%,.1f", totalLength) + "\n"
                    + "Ngày tạo: " + dateFormat.format(exportSlip.getCreateDate()) + "\n\n"
                    + "Trân trọng !";
            emailSender.sendEmail(email, subject, content);

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
