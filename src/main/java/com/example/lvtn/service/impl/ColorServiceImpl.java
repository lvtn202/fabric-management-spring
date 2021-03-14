package com.example.lvtn.service.impl;

import com.example.lvtn.dao.ColorRepository;
import com.example.lvtn.dom.Color;
import com.example.lvtn.service.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColorServiceImpl implements ColorService {
    @Autowired
    private ColorRepository colorRepository;

    @Override
    public List<Color> findAll() {
        return colorRepository.findAll();
    }
}
