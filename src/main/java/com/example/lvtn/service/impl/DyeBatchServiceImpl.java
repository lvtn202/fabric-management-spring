package com.example.lvtn.service.impl;

import com.example.lvtn.dao.DyeBatchRepository;
import com.example.lvtn.dom.DyeBatch;
import com.example.lvtn.service.DyeBatchService;
import com.example.lvtn.service.DyehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DyeBatchServiceImpl implements DyeBatchService {
    @Autowired
    DyeBatchRepository dyeBatchRepository;

    @Override
    public List<DyeBatch> findAll() {
        return dyeBatchRepository.findAll();
    }
}
