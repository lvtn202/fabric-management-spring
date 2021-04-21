package com.example.lvtn.service;


import com.example.lvtn.dom.FabricType;
import com.example.lvtn.dto.FabricTypeAndColorDTO;
import com.example.lvtn.dto.FabricTypeDTO;
import com.example.lvtn.utils.InternalException;
import org.springframework.stereotype.Service;

import java.util.List;

public interface FabricTypeService {
    List<FabricType> findAll();

    List<FabricTypeDTO> findFabricTypes() throws InternalException;
}
