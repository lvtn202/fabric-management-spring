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
    private String name;

    @OneToMany(mappedBy = "fabricType")
    private Set<Color> colors;

    public FabricType() {}

    public FabricType(String name) {
        this.name = name;
    }
    public FabricType(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "FabricType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
