package com.example.lvtn.dom;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "color")
@Getter
@Setter
public class Color {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10)
    private String type;

    @Column(length = 50)
    private String name;

    @Column(name = "hexa_code", length = 6, nullable = false)
    private String hexaCode;

    @Column
    private String recipe;

    @Column(nullable = false)
    private Double price;

    @ManyToOne
    @JoinColumn(name = "fabric_type_id", nullable = false)
    private FabricType fabricType;

    @OneToMany(mappedBy = "color")
    private Set<DyeBatch> dyeBatches;

    @OneToMany(mappedBy = "color")
    private Set<Fabric> fabrics;

    public Color() {
    }

    public Color(String type, String name, String hexaCode, String recipe, Double price, FabricType fabricType, Set<DyeBatch> dyeBatches, Set<Fabric> fabrics) {
        this.type = type;
        this.name = name;
        this.hexaCode = hexaCode;
        this.recipe = recipe;
        this.price = price;
        this.fabricType = fabricType;
        this.dyeBatches = dyeBatches;
        this.fabrics = fabrics;
    }

    @Override
    public String toString() {
        return "Color{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", hexaCode='" + hexaCode + '\'' +
                ", recipe='" + recipe + '\'' +
                ", price=" + price +
                ", fabricType=" + fabricType +
                '}';
    }
}
