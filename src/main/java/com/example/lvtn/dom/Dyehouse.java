package com.example.lvtn.dom;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "dyehouse")
@Getter
@Setter
//@Document(indexName = "dyehouseindex")
public class Dyehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String nameEN;

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

    @OneToMany(mappedBy = "dyehouse")
    private Set<Payment> payments;

    @OneToMany(mappedBy = "dyehouse")
    private Set<Fabric> fabrics;

    @OneToMany(mappedBy = "dyehouse")
    private Set<ReturnSlip> returnSlips;

    @OneToMany(mappedBy = "dyehouse")
    private Set<Debt> debts;

    public Dyehouse() {
    }

    public Dyehouse(String name, String nameEN, String address, String phoneNumber, String email, Double debt, Set<ExportSlip> exportSlips, Set<DyeBatch> dyeBatches, Set<Payment> payments, Set<Fabric> fabrics, Set<ReturnSlip> returnSlips) {
        this.name = name;
        this.nameEN = nameEN;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.debt = debt;
        this.exportSlips = exportSlips;
        this.dyeBatches = dyeBatches;
        this.payments = payments;
        this.fabrics = fabrics;
        this.returnSlips = returnSlips;
    }

    @Override
    public String toString() {
        return "Dyehouse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nameEN='" + nameEN + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", debt=" + debt +
                '}';
    }
}
