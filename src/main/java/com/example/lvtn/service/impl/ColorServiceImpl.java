package com.example.lvtn.service.impl;

import com.example.lvtn.dao.ColorRepository;
import com.example.lvtn.dom.Color;
import com.example.lvtn.service.ColorService;
import com.example.lvtn.utils.InternalException;
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

    @Override
    public String getPriceByFabricTypeAndColor(String fabricType, String color) throws InternalException {
        try {
            Color currentColor = colorRepository.findColorByFabricTypeAndColor(fabricType, color);
            return String.format("%.3f", currentColor.getPrice());
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalException(e.getMessage());
        }
    }
}
