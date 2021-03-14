package com.example.lvtn.service.impl;

import com.example.lvtn.dao.FabricRepository;
import com.example.lvtn.dom.Fabric;
import com.example.lvtn.service.FabricService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FabricServiceImpl implements FabricService {
    @Autowired
    FabricRepository fabricRepository;

    @Override
    public List<Fabric> findAll() {
        return fabricRepository.findAll();
    }
}
