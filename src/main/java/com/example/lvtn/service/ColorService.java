package com.example.lvtn.service;

import com.example.lvtn.dom.Color;
import com.example.lvtn.utils.InternalException;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ColorService {
    List<Color> findAll();

    String getPriceByFabricTypeAndColor(String fabricType, String color) throws InternalException;
}
