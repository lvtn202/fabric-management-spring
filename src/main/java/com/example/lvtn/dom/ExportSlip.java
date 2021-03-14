package com.example.lvtn.dom;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "export_slip")
@Getter
@Setter
public class ExportSlip {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "fabric_number", nullable = false)
    private Long fabricNumber;

    @Column(name = "create_date", nullable = false)
    private Timestamp createDate;

    @ManyToOne
    @JoinColumn(name = "dyehouse_id", nullable = false)
    private Dyehouse dyehouse;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @OneToMany(mappedBy = "exportSlip")
    private Set<Fabric> fabrics;

    public ExportSlip() {
    }

    public ExportSlip(Long id, Long fabricNumber, Timestamp createDate, Dyehouse dyehouse, Employee employee, Set<Fabric> fabrics) {
        this.id = id;
        this.fabricNumber = fabricNumber;
        this.createDate = createDate;
        this.dyehouse = dyehouse;
        this.employee = employee;
        this.fabrics = fabrics;
    }

    @Override
    public String toString() {
        return "ExportSlip{" +
                "id=" + id +
                ", fabricNumber=" + fabricNumber +
                ", createDate=" + createDate +
                ", dyehouse=" + dyehouse +
                ", employee=" + employee +
                '}';
    }
}
