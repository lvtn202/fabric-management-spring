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
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @OneToMany(mappedBy = "importSlip")
    private Set<DyeBatch> dyeBatches;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "import_slip_orders",
                joinColumns = {@JoinColumn(name = "import_slip_id")},
                inverseJoinColumns = {@JoinColumn(name = "orders_id")})
    private Set<Order> orders = new HashSet<>();

    public ImportSlip() {
    }

    public ImportSlip(Long id, Double money, Long fabricNumber, Timestamp createDate, Employee employee, Set<DyeBatch> dyeBatches, Set<Order> orders) {
        this.id = id;
        this.money = money;
        this.fabricNumber = fabricNumber;
        this.createDate = createDate;
        this.employee = employee;
        this.dyeBatches = dyeBatches;
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "ImportSlip{" +
                "id=" + id +
                ", money=" + money +
                ", fabricNumber=" + fabricNumber +
                ", createDate=" + createDate +
                ", employee=" + employee +
                '}';
    }
}
