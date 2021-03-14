package com.example.lvtn.service.impl;

import com.example.lvtn.dao.ReturnSlipRepository;
import com.example.lvtn.dom.ReturnSlip;
import com.example.lvtn.service.ReturnSlipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReturnSlipServiceImpl implements ReturnSlipService {
    @Autowired
    private ReturnSlipRepository returnSlipRepository;

    @Override
    public List<ReturnSlip> findAll() {
        return returnSlipRepository.findAll();
    }
}
