package com.example.lvtn.service.impl;


import com.example.lvtn.dao.FabricTypeRepository;
import com.example.lvtn.dom.FabricType;
import com.example.lvtn.service.FabricTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FabricTypeServiceImpl implements FabricTypeService {
    @Autowired
    public FabricTypeRepository fabricTypeRepository;

    @Override
    public List<FabricType> findAll() {
        return fabricTypeRepository.findAll();
    }
}
