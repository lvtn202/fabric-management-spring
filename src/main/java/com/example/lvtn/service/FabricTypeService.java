package com.example.lvtn.service;


import com.example.lvtn.dom.FabricType;
import org.springframework.stereotype.Service;

import java.util.List;

public interface FabricTypeService {
    List<FabricType> findAll();
}
