package com.example.lvtn.service;

import com.example.lvtn.dom.Color;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ColorService {
    List<Color> findAll();
}
