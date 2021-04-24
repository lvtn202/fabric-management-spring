package com.example.lvtn.service.impl;

import com.example.lvtn.dao.ReturnRepository;
import com.example.lvtn.dom.Return;
import com.example.lvtn.dto.ReturnDTO;
import com.example.lvtn.service.ReturnService;
import com.example.lvtn.utils.InternalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReturnServiceImpl implements ReturnService {
    @Autowired
    ReturnRepository returnRepository;

    @Override
    public List<Return> findAll() {
        return returnRepository.findAll();
    }

    @Override
    public List<ReturnDTO> findReturnDTOs(Long returnSlipId, Long pageIndex, Long pageSize) throws InternalException {
        try {
            List<Return> listReturn = new ArrayList<>();
            if (returnSlipId < 0L){
                listReturn = returnRepository.findReturns(pageIndex, pageSize);
            } else {
                listReturn = returnRepository.findReturnsByReturnSlipId(returnSlipId, pageIndex, pageSize);
            }

            List<ReturnDTO> listReturnDTO = new ArrayList<>();
            for (Return aReturn: listReturn){
                listReturnDTO.add(ReturnDTO.convertReturnToReturnDTO(aReturn));
            }
            return  listReturnDTO;
        } catch (Exception e){
            e.printStackTrace();
            throw new InternalException(e.getMessage());
        }
    }
}
