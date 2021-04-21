package com.example.lvtn.service.impl;


import com.example.lvtn.dao.ColorRepository;
import com.example.lvtn.dao.FabricTypeRepository;
import com.example.lvtn.dom.Color;
import com.example.lvtn.dom.Dyehouse;
import com.example.lvtn.dom.FabricType;
import com.example.lvtn.dto.FabricTypeAndColorDTO;
import com.example.lvtn.dto.FabricTypeDTO;
import com.example.lvtn.service.FabricTypeService;
import com.example.lvtn.utils.InternalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Service
public class FabricTypeServiceImpl implements FabricTypeService {
    @Autowired
    private FabricTypeRepository fabricTypeRepository;

    @Autowired
    private ColorRepository colorRepository;

    @Override
    public List<FabricType> findAll() {
        return fabricTypeRepository.findAll();
    }

    @Override
    public List<FabricTypeDTO> findFabricTypes() throws InternalException {
        try {
            List<FabricType> listFabricType = fabricTypeRepository.findAll();
            List<FabricTypeDTO> listFabricTypeDTO = new ArrayList<>();
            for (FabricType fabricType: listFabricType){
                List<Color> listColorOfFabricType = colorRepository.findColorsByFabricTypeId(fabricType.getId());
                List<String> colors = new ArrayList<>();
                for(Color color: listColorOfFabricType){
                    colors.add(color.getName());
                }
                listFabricTypeDTO.add(FabricTypeDTO.convertFabricTypeToFabricTypeDTO(fabricType, colors));
            }
            return listFabricTypeDTO;
        } catch (Exception e){
            e.printStackTrace();
            throw new InternalException(e.getMessage());
        }
    }

}
