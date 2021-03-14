package com.example.lvtn.service.impl;

import com.example.lvtn.dao.DyehouseRepository;
import com.example.lvtn.dom.Dyehouse;
import com.example.lvtn.service.DyehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DyehouseServiceImpl implements DyehouseService {
    @Autowired
    private DyehouseRepository dyehouseRepository;

    @Override
    public List<Dyehouse> findAll() {
        return dyehouseRepository.findAll();
    }
}
