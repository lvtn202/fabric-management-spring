package com.example.lvtn.service.impl;

import com.example.lvtn.dao.*;
import com.example.lvtn.dom.*;
import com.example.lvtn.dto.CreateImportSlipForm;
import com.example.lvtn.dto.FabricCreateImportSlip;
import com.example.lvtn.dto.ImportSlipDTO;
import com.example.lvtn.service.ImportSlipService;
import com.example.lvtn.utils.FabricStatus;
import com.example.lvtn.utils.InternalException;
import com.example.lvtn.utils.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ImportSlipServiceImpl implements ImportSlipService {
    @Autowired
    private ImportSlipRepository importSlipRepository;

    @Autowired
    private FabricRepository fabricRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DyehouseRepository dyehouseRepository;

    @Autowired
    private ColorRepository colorRepository;

    @Autowired
    private DyeBatchRepository dyeBatchRepository;

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

    @Override
    @Transactional(rollbackOn = Exception.class)
    public ModelMap createImportSlip(CreateImportSlipForm createImportSlipForm) throws InternalException {
        try {
            Order order = orderRepository.findOrderById(createImportSlipForm.getOrderId());
            Set<Fabric> fabrics = new HashSet<>();
            Color color = colorRepository.findColorByFabricTypeAndColor(createImportSlipForm.getFabricType(),createImportSlipForm.getColor());
            Double importLength = Double.valueOf(0);

            for (FabricCreateImportSlip fabricCreateImportSlip: createImportSlipForm.getFabrics()){
                Fabric fabric = fabricRepository.findFabricById(fabricCreateImportSlip.getId());
                fabric.setFinishedLength(fabricCreateImportSlip.getFinishedLength());
                fabric.setStatus(FabricStatus.COMPLETED);
                importLength += fabric.getFinishedLength();
                fabrics.add(fabric);
            }
            order.setDoneLength(order.getDoneLength() + importLength);
            if (order.getDoneLength() >= order.getOrderLength()){
                order.setStatus(OrderStatus.COMPLETED);
            } else {
                order.setStatus(OrderStatus.IN_PROGRESS);
            }
            ImportSlip newImportSlip = new ImportSlip(
                    importLength,
                    (long) createImportSlipForm.getFabrics().size(),
                    createImportSlipForm.getDriver(),
                    createImportSlipForm.getCreateDate(),
                    order,
                    userRepository.findUsersById(createImportSlipForm.getUserId()),
                    new HashSet<DyeBatch>()
            );
            DyeBatch newDyeBatch = new DyeBatch(
                    createImportSlipForm.getCreateDate(),
                    color,
                    dyehouseRepository.findDyehouseById(createImportSlipForm.getDyehouseId()),
                    newImportSlip,
                    fabrics
            );

            for (Fabric fabric: fabrics){
                fabric.setDyeBatch(newDyeBatch);
                fabric.setColor(color);
                fabricRepository.save(fabric);
            }
            orderRepository.save(order);
            importSlipRepository.save(newImportSlip);
            dyeBatchRepository.save(newDyeBatch);


            ModelMap modelMap = new ModelMap();
            modelMap.addAttribute("importSlipId", newImportSlip.getId());
            modelMap.addAttribute("money", String.format("%.3f", newImportSlip.getMoney()));
            modelMap.addAttribute("fabricNumber", newImportSlip.getFabricNumber());
            modelMap.addAttribute("driver", newImportSlip.getDriver());
            modelMap.addAttribute("createDate", String.format("%tQ", newImportSlip.getCreateDate()));
            modelMap.addAttribute("orderId", newImportSlip.getOrder().getId());
            modelMap.addAttribute("firstName", newImportSlip.getUser().getFirstName());
            modelMap.addAttribute("lastName", newImportSlip.getUser().getLastName());
            return modelMap;

        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalException(e.getMessage());
        }
    }

    @Override
    public List<ImportSlipDTO> findRecentImportSlipDTOs(Long dyehouseId, Long pageIndex, Long pageSize) throws InternalException {
        try {
            List<ImportSlip> listImportSlip = new ArrayList<>();
            List<ImportSlipDTO> listImportSlipDTO = new ArrayList<ImportSlipDTO>();
            if(dyehouseId < 0){
                listImportSlip = importSlipRepository.findRecentImportSlips(pageIndex, pageSize);
            } else {
                listImportSlip = importSlipRepository.findRecentImportSlipsInDyehouse(dyehouseId, pageIndex, pageSize);
            }

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
