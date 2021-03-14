package com.example.lvtn.service.impl;

import com.example.lvtn.dao.ImportSlipRepository;
import com.example.lvtn.dom.ImportSlip;
import com.example.lvtn.service.ImportSlipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImportSlipServiceImpl implements ImportSlipService {
    @Autowired
    private ImportSlipRepository importSlipRepository;

    @Override
    public List<ImportSlip> findAll() {
        return importSlipRepository.findAll();
    }
}
