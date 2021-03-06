package com.example.lvtn.dom;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "return_slip")
@Getter
@Setter
public class ReturnSlip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "return_date", nullable = false)
    private Timestamp returnDate;

    @Column(nullable = false)
    private Double money;

    @Column(name = "received_name", nullable = false)
    private String receivedName;

    @ManyToOne
    @JoinColumn(name = "dyehouse_id", nullable = false)
    private Dyehouse dyehouse;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "returnSlip")
    private Set<Return> returns;

    public ReturnSlip() {
    }

    public ReturnSlip(Timestamp returnDate, Double money, String receivedName, Dyehouse dyehouse, User user, Set<Return> returns) {
        this.returnDate = returnDate;
        this.money = money;
        this.receivedName = receivedName;
        this.dyehouse = dyehouse;
        this.user = user;
        this.returns = returns;
    }

    @Override
    public String toString() {
        return "ReturnSlip{" +
                "id=" + id +
                ", returnDate=" + returnDate +
                ", money=" + money +
                ", receivedName=" + receivedName +
                ", dyehouse=" + dyehouse +
                ", user=" + user +
                '}';
    }
}
