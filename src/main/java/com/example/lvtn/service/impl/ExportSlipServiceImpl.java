package com.example.lvtn.service.impl;

import com.example.lvtn.dao.ExportSlipRepository;
import com.example.lvtn.dom.ExportSlip;
import com.example.lvtn.service.ExportSlipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExportSlipServiceImpl implements ExportSlipService {
    @Autowired
    ExportSlipRepository exportSlipRepository;

    @Override
    public List<ExportSlip> findAll() {
        return exportSlipRepository.findAll();
    }
}
