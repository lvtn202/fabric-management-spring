package com.example.lvtn.dom;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "dyehouse")
@Getter
@Setter
public class Dyehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String address;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(length = 50)
    private String email;

    @Column(nullable = false)
    private Double debt;

    @OneToMany(mappedBy = "dyehouse")
    private Set<ExportSlip> exportSlips;

    @OneToMany(mappedBy = "dyehouse")
    private Set<DyeBatch> dyeBatches;

    public Dyehouse() {
    }

    @Override
    public String toString() {
        return "Dyehouse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", debt=" + debt +
                '}';
    }
}
