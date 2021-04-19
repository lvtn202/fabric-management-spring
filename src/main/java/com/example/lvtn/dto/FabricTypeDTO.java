package com.example.lvtn.dto;

import com.example.lvtn.dom.FabricType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FabricTypeDTO {
    private Long id;

    private String type;

    private String name;

    public FabricTypeDTO() {
    }

    public FabricTypeDTO(Long id, String type, String name) {
        this.id = id;
        this.type = type;
        this.name = name;
    }

    @Override
    public String toString() {
        return "FabricTypeDTO{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    static public FabricTypeDTO convertFabricTypeToFabricTypeDTO(FabricType fabricType){
        return new FabricTypeDTO(fabricType.getId(), fabricType.getType(), fabricType.getName());
    }
}
