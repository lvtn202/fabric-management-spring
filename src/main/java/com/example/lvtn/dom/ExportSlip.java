package com.example.lvtn.dom;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

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

    public ExportSlip() {
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
