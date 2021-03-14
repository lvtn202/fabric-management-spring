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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 50)
    private String name;

    @Column(name = "hexa_code", length = 6, nullable = false)
    private String hexaCode;

    @Column(nullable = false)
    private Double price;

    @ManyToOne
    @JoinColumn(name = "fabric_type_id", nullable = false)
    private FabricType fabricType;

    @OneToMany(mappedBy = "color")
    private Set<DyeBatch> dyeBatches;

    public Color() {
    }

    @Override
    public String toString() {
        return "Color{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", hexaCode='" + hexaCode + '\'' +
                ", price=" + price +
                ", fabricType=" + fabricType +
                '}';
    }
}
