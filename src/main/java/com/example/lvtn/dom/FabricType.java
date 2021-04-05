package com.example.lvtn.dom;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "fabric_type")
@Getter
@Setter
public class FabricType {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(length = 20, nullable = false)
    private String type;

    @Column(length = 20, nullable = false)
    private String name;

    @OneToMany(mappedBy = "fabricType")
    private Set<Color> colors;

    @OneToMany(mappedBy = "fabricType")
    private Set<Fabric> fabrics;

    public FabricType() {
    }

    public FabricType(String type, String name, Set<Color> colors, Set<Fabric> fabrics) {
        this.type = type;
        this.name = name;
        this.colors = colors;
        this.fabrics = fabrics;
    }

    @Override
    public String toString() {
        return "FabricType{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
