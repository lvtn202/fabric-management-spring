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
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public ReturnSlip() {
    }

    public ReturnSlip(Timestamp returnDate, Double money, Dyehouse dyehouse, User user) {
        this.returnDate = returnDate;
        this.money = money;
        this.dyehouse = dyehouse;
        this.user = user;
    }

    @Override
    public String toString() {
        return "ReturnSlip{" +
                "id=" + id +
                ", returnDate=" + returnDate +
                ", money=" + money +
                ", dyehouse=" + dyehouse +
                ", user=" + user +
                '}';
    }
}
