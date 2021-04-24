package com.example.lvtn.service.impl;

import com.example.lvtn.dao.ReturnSlipRepository;
import com.example.lvtn.dom.ReturnSlip;
import com.example.lvtn.dto.ReturnSlipDTO;
import com.example.lvtn.service.ReturnSlipService;
import com.example.lvtn.utils.InternalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReturnSlipServiceImpl implements ReturnSlipService {
    @Autowired
    private ReturnSlipRepository returnSlipRepository;

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
}
