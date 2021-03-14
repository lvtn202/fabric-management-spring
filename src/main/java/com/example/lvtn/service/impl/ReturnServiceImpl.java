package com.example.lvtn.service.impl;

import com.example.lvtn.dao.ReturnRepository;
import com.example.lvtn.dom.Return;
import com.example.lvtn.service.ReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReturnServiceImpl implements ReturnService {
    @Autowired
    ReturnRepository returnRepository;

    @Override
    public List<Return> findAll() {
        return returnRepository.findAll();
    }
}
