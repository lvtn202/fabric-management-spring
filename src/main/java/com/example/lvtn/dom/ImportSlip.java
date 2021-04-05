package com.example.lvtn.dom;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "import_slip")
@Getter
@Setter
public class ImportSlip {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Double money;

    @Column(name = "fabric_number", nullable = false)
    private Long fabricNumber;

    @Column(name = "create_date", nullable = false)
    private Timestamp createDate;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @OneToMany(mappedBy = "importSlip")
    private Set<DyeBatch> dyeBatches;

    public ImportSlip() {
    }

    public ImportSlip(Double money, Long fabricNumber, Timestamp createDate, Order order, Employee employee, Set<DyeBatch> dyeBatches) {
        this.money = money;
        this.fabricNumber = fabricNumber;
        this.createDate = createDate;
        this.order = order;
        this.employee = employee;
        this.dyeBatches = dyeBatches;
    }

    @Override
    public String toString() {
        return "ImportSlip{" +
                "id=" + id +
                ", money=" + money +
                ", fabricNumber=" + fabricNumber +
                ", createDate=" + createDate +
                ", order=" + order +
                ", employee=" + employee +
                '}';
    }
}
