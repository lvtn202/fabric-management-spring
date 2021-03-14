package com.example.lvtn.dom;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 20, nullable = false)
    private String status;

    @Column(name = "create_date", nullable = false)
    private Timestamp createDate;

    @Column(name = "order_length", nullable = false)
    private Double orderLength;

    @ManyToOne
    @JoinColumn(name = "dyehouse_id", nullable = false)
    private Dyehouse dyehouse;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "color_id", nullable = false)
    private Color color;

    @ManyToMany(mappedBy = "orders")
    private Set<ImportSlip> importSlips = new HashSet<>();

    public Order() {
    }

    public Order(Long id, String status, Timestamp createDate, Double orderLength, Dyehouse dyehouse, Employee employee, Color color, Set<ImportSlip> importSlips) {
        this.id = id;
        this.status = status;
        this.createDate = createDate;
        this.orderLength = orderLength;
        this.dyehouse = dyehouse;
        this.employee = employee;
        this.color = color;
        this.importSlips = importSlips;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", status='" + status + '\'' +
                ", createDate=" + createDate +
                ", orderLength=" + orderLength +
                ", dyehouse=" + dyehouse +
                ", employee=" + employee +
                ", color=" + color +
                '}';
    }
}
