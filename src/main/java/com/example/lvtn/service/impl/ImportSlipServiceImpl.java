package com.example.lvtn.service.impl;

import com.example.lvtn.dao.*;
import com.example.lvtn.dom.*;
import com.example.lvtn.dto.CreateImportSlipForm;
import com.example.lvtn.dto.FabricCreateImportSlip;
import com.example.lvtn.dto.ImportSlipDTO;

import com.example.lvtn.service.DebtService;
import com.example.lvtn.service.ImportSlipService;
import com.example.lvtn.utils.EmailSender;
import com.example.lvtn.utils.FabricStatus;
import com.example.lvtn.utils.InternalException;
import com.example.lvtn.utils.OrderStatus;
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

    @Autowired
    private DebtService debtService;

    @Autowired
    public EmailSender emailSender;

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
            Double totalPrice = 0.0;
            Dyehouse currentDyehouse = dyehouseRepository.findDyehouseById(createImportSlipForm.getDyehouseId());
            Double oldDebt = currentDyehouse.getDebt();

            for (FabricCreateImportSlip fabricCreateImportSlip: createImportSlipForm.getFabrics()){
                Fabric fabric = fabricRepository.findFabricById(fabricCreateImportSlip.getId());
                fabric.setFinishedLength(fabricCreateImportSlip.getFinishedLength());
                fabric.setStatus(FabricStatus.COMPLETED);
                fabric.setColor(color);
                importLength += fabric.getFinishedLength();
                fabrics.add(fabric);
                totalPrice += fabric.getFinishedLength() *  fabric.getColor().getPrice();
            }
            order.setDoneLength(order.getDoneLength() + importLength);
            if (order.getDoneLength() >= order.getOrderLength()){
                order.setStatus(OrderStatus.COMPLETED);
            } else {
                order.setStatus(OrderStatus.IN_PROGRESS);
            }
            ImportSlip newImportSlip = new ImportSlip(
                    totalPrice,
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
                    currentDyehouse,
                    newImportSlip,
                    fabrics
            );
            currentDyehouse.setDebt(currentDyehouse.getDebt() + totalPrice);

            for (Fabric fabric: fabrics){
                fabric.setDyeBatch(newDyeBatch);
                fabricRepository.save(fabric);
            }
            dyehouseRepository.save(currentDyehouse);
            orderRepository.save(order);
            importSlipRepository.save(newImportSlip);
            dyeBatchRepository.save(newDyeBatch);

            debtService.createDebt(1L, currentDyehouse, newImportSlip.getId(), newImportSlip.getMoney(), newImportSlip.getCreateDate(), oldDebt);

            String email = dyehouseRepository.findDyehouseById(createImportSlipForm.getDyehouseId()).getEmail();
            String subject = "Phiếu nhập vải mới";
            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+7"));
            String content = "Xin chào xưởng " + dyehouseRepository.findDyehouseById(createImportSlipForm.getDyehouseId()).getName() + ",\n\n"
                    + "Phiếu nhập vải mới đã được tạo.\n"
                    + "Mã phiếu nhập: " + newImportSlip.getId().toString() + "\n"
                    + "Số lượng cây vải: " + newImportSlip.getFabricNumber().toString() + "\n"
                    + "Tổng độ dài: " + String.format("%,.1f", importLength) + " (m)\n"
                    + "Người giao hàng: " + newImportSlip.getDriver() + "\n"
                    + "Ngày tạo: " + dateFormat.format(newImportSlip.getCreateDate()) + "\n"
                    + "Trân trọng !";
            emailSender.sendEmail(email, subject, content);

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
