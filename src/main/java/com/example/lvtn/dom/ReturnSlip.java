package com.example.lvtn.dom;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "return_slip")
@Getter
@Setter
public class ReturnSlip {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "return_date", nullable = false)
    private Timestamp returnDate;

    @Column(nullable = false)
    private Double money;

    @ManyToOne
    @JoinColumn(name = "dyehouse_id", nullable = false)
    private Dyehouse dyehouse;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    public ReturnSlip() {
    }

    public ReturnSlip(Long id, Timestamp returnDate, Double money, Dyehouse dyehouse, Employee employee) {
        this.id = id;
        this.returnDate = returnDate;
        this.money = money;
        this.dyehouse = dyehouse;
        this.employee = employee;
    }

    @Override
    public String toString() {
        return "ReturnSlip{" +
                "id=" + id +
                ", returnDate=" + returnDate +
                ", money=" + money +
                ", dyehouse=" + dyehouse +
                ", employee=" + employee +
                '}';
    }
}
