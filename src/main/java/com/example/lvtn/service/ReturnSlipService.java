package com.example.lvtn.service;

import com.example.lvtn.dom.ReturnSlip;
import com.example.lvtn.dto.CreateReturnSlipForm;
import com.example.lvtn.dto.ReturnSlipDTO;
import com.example.lvtn.utils.InternalException;
import org.springframework.ui.ModelMap;

import java.util.List;

public interface ReturnSlipService {
    List<ReturnSlip> findAll();

    List<ReturnSlipDTO> findReturnSlipDTOsWithPaging(Long pageIndex, Long pageSize) throws InternalException;

    ModelMap findDetailReturnSlip(Long returnSlipId) throws InternalException;

    ModelMap createReturnSlip(CreateReturnSlipForm createReturnSlipForm) throws InternalException;
}
