package com.example.lvtn.service;

import com.example.lvtn.dom.Return;
import com.example.lvtn.dto.ReturnDTO;
import com.example.lvtn.utils.InternalException;

import java.util.List;

public interface ReturnService {
    List<Return> findAll();

    List<ReturnDTO> findReturnDTOs(Long returnSlipId, Long pageIndex, Long pageSize) throws InternalException;
}
