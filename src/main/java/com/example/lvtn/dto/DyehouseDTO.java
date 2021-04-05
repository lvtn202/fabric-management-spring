package com.example.lvtn.dto;

import com.example.lvtn.dom.Dyehouse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DyehouseDTO {
    private Long id;

    private String name;

    private String debt;

    private String inStock;

    public DyehouseDTO(Long id, String name, String debt, String inStock) {
        this.id = id;
        this.name = name;
        this.debt = debt;
        this.inStock = inStock;
    }

    @Override
    public String toString() {
        return "DyehouseDTO{" +
                "id=" + id +
                "name=" + name +
                ", debt=" + debt +
                ", inStock=" + inStock +
                '}';
    }

    static public DyehouseDTO convertDyehouseToDyehouseDTO(Dyehouse dyehouse){
        return new DyehouseDTO(dyehouse.getId(), dyehouse.getName(), String.format("%.1f", dyehouse.getDebt()), "0.0");
    }
}
