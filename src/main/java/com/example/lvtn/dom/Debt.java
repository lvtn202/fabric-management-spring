package com.example.lvtn.dom;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "debt")
@Getter
@Setter
public class Debt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long type;

    @ManyToOne
    @JoinColumn(name = "dyehouse_id", nullable = false)
    private Dyehouse dyehouse;

    @Column(name = "id_transaction", nullable = false)
    private Long idTransaction;

    @Column(nullable = false)
    private Double amount;

    @Column(name = "create_date", nullable = false)
    private Timestamp createDate;

    @Column(nullable = false)
    private Double total;

    public Debt() {
    }

    public Debt(Long type, Dyehouse dyehouse, Long idTransaction, Double amount, Timestamp createDate, Double total) {
        this.type = type;
        this.dyehouse = dyehouse;
        this.idTransaction = idTransaction;
        this.amount = amount;
        this.createDate = createDate;
        this.total = total;
    }

    @Override
    public String toString() {
        return "Debt{" +
                "id=" + id +
                ", type=" + type +
                ", dyehouse=" + dyehouse +
                ", idTransaction=" + idTransaction +
                ", amount=" + amount +
                ", createDate=" + createDate +
                ", total=" + total +
                '}';
    }
}
