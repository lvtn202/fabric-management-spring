package com.example.lvtn.dom;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "import_slip")
@Getter
@Setter
public class ImportSlip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "importSlip")
    private Set<DyeBatch> dyeBatches;

    public ImportSlip() {
    }

    public ImportSlip(Double money, Long fabricNumber, Timestamp createDate, Order order, User user, Set<DyeBatch> dyeBatches) {
        this.money = money;
        this.fabricNumber = fabricNumber;
        this.createDate = createDate;
        this.order = order;
        this.user = user;
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
                ", user=" + user +
                '}';
    }
}
