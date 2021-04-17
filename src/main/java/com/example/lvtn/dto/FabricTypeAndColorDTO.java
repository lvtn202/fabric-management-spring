package com.example.lvtn.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FabricTypeAndColorDTO {
    private String fabricType;

    private List<String> colors;

    public FabricTypeAndColorDTO(String fabricType, List<String> colors) {
        this.fabricType = fabricType;
        this.colors = colors;
    }

    @Override
    public String toString() {
        return "FabricTypeAndColorDTO{" +
                "fabricType='" + fabricType + '\'' +
                ", colors=" + colors +
                '}';
    }
}
