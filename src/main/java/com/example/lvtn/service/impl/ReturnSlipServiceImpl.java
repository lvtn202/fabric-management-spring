package com.example.lvtn.service.impl;

import com.example.lvtn.dao.*;
import com.example.lvtn.dom.Dyehouse;
import com.example.lvtn.dom.Fabric;
import com.example.lvtn.dom.Return;
import com.example.lvtn.dom.ReturnSlip;
import com.example.lvtn.dto.CreateReturnSlipForm;
import com.example.lvtn.dto.FabricCreateReturnSlip;
import com.example.lvtn.dto.ReturnSlipDTO;
import com.example.lvtn.service.ReturnSlipService;
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
public class ReturnSlipServiceImpl implements ReturnSlipService {
    @Autowired
    private ReturnSlipRepository returnSlipRepository;

    @Autowired
    private FabricRepository fabricRepository;

    @Autowired
    private DyehouseRepository dyehouseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReturnRepository returnRepository;

    @Override
    public List<ReturnSlip> findAll() {
        return returnSlipRepository.findAll();
    }

    @Override
    public List<ReturnSlipDTO> findReturnSlipDTOsWithPaging(Long pageIndex, Long pageSize) throws InternalException {
        try {
            List<ReturnSlip> listReturnSlip = returnSlipRepository.findReturnSlipsWithPaging(pageIndex, pageSize);
            List<ReturnSlipDTO> listReturnSlipDTO = new ArrayList<>();
            for (ReturnSlip returnSlip: listReturnSlip){
                listReturnSlipDTO.add(ReturnSlipDTO.convertReturnSlipToReturnSlipDTO(returnSlip));
            }
            return listReturnSlipDTO;
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalException(e.getMessage());
        }
    }

    @Override
    public ModelMap findDetailReturnSlip(Long returnSlipId) throws InternalException {
        try {
            ReturnSlip returnSlip = returnSlipRepository.findReturnSlipById(returnSlipId);
            ModelMap modelMap = new ModelMap();
            modelMap.addAttribute("returnSlipId", returnSlip.getId());
            modelMap.addAttribute("dyehouseName", returnSlip.getDyehouse().getName());
            modelMap.addAttribute("money", String.format("%.3f", returnSlip.getMoney()));
            modelMap.addAttribute("receivedName", returnSlip.getReceivedName());
            modelMap.addAttribute("returnDate", String.format("%tQ", returnSlip.getReturnDate()));
            modelMap.addAttribute("firstName", returnSlip.getUser().getFirstName());
            modelMap.addAttribute("lastName", returnSlip.getUser().getLastName());
            return modelMap;
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalException(e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public ModelMap createReturnSlip(CreateReturnSlipForm createReturnSlipForm) throws InternalException {
        try {
            Set<Return> returns = new HashSet<>();
            Double totalPrice = 0.0;

            for (FabricCreateReturnSlip fabricCreateReturnSlip: createReturnSlipForm.getFabrics()){
                Fabric currentFabric = fabricRepository.findFabricById(fabricCreateReturnSlip.getFabricId());
                currentFabric.setStatus(FabricStatus.RETURNED);
                fabricRepository.save(currentFabric);
                totalPrice += fabricCreateReturnSlip.getMoney();
                Return newReturn = new Return(
                        fabricCreateReturnSlip.getReturnLength(),
                        fabricCreateReturnSlip.getReturnReason(),
                        fabricCreateReturnSlip.getMoney(),
                        currentFabric,
                        null
                );
                returns.add(newReturn);
            }
            ReturnSlip newReturnSlip = new ReturnSlip(
                    createReturnSlipForm.getReturnDate(),
                    totalPrice,
                    createReturnSlipForm.getReceivedName(),
                    dyehouseRepository.findDyehouseById(createReturnSlipForm.getDyehouseId()),
                    userRepository.findUsersById(createReturnSlipForm.getUserId()),
                    returns
            );
            Dyehouse dyehouse = dyehouseRepository.findDyehouseById(createReturnSlipForm.getDyehouseId());
            dyehouse.setDebt(dyehouse.getDebt() - totalPrice);

            dyehouseRepository.save(dyehouse);
            returnSlipRepository.save(newReturnSlip);
            for (Return aReturn: returns){
                aReturn.setReturnSlip(newReturnSlip);
                returnRepository.save(aReturn);
            }

            ModelMap modelMap = new ModelMap();
            modelMap.addAttribute("returnSlipId", newReturnSlip.getId());
            modelMap.addAttribute("returnDate", String.format("%tQ", newReturnSlip.getReturnDate()));
            modelMap.addAttribute("money", String.format("%.3f", newReturnSlip.getMoney()));
            modelMap.addAttribute("receivedName", newReturnSlip.getReceivedName());
            modelMap.addAttribute("dyehouseName", newReturnSlip.getDyehouse().getName());
            modelMap.addAttribute("firstName", newReturnSlip.getUser().getFirstName());
            modelMap.addAttribute("lastName", newReturnSlip.getUser().getLastName());
            System.out.println(modelMap.toString());
            return modelMap;
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalException(e.getMessage());
        }
    }
}
