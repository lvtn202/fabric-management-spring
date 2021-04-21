package com.example.lvtn.dto;

import com.example.lvtn.dao.ColorRepository;
import com.example.lvtn.dom.Color;
import com.example.lvtn.dom.FabricType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class FabricTypeDTO {
    private Long id;

    private String type;

    private String name;

    private List<String> colors;

    public FabricTypeDTO() {
    }

    public FabricTypeDTO(Long id, String type, String name, List<String> colors) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.colors = colors;
    }

    @Override
    public String toString() {
        return "FabricTypeDTO{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    static public FabricTypeDTO convertFabricTypeToFabricTypeDTO(FabricType fabricType, List<String> colors){
        return new FabricTypeDTO(fabricType.getId(), fabricType.getType(), fabricType.getName(), colors);
    }
}
