package com.example.lvtn.dom;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "returns")
@Getter
@Setter
public class Return {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "return_length", nullable = false)
    private Double returnLength;

    @Column(name = "return_reason")
    private String returnReason;

    @Column(nullable = false)
    private Double money;

    @ManyToOne
    @JoinColumn(name = "fabric_id", nullable = false, unique = true)
    private Fabric fabric;

    @ManyToOne
    @JoinColumn(name = "return_slip_id", nullable = false)
    private ReturnSlip returnSlip;

    public Return() {
    }

    public Return(Double returnLength, String returnReason, Double money, Fabric fabric, ReturnSlip returnSlip) {
        this.returnLength = returnLength;
        this.returnReason = returnReason;
        this.money = money;
        this.fabric = fabric;
        this.returnSlip = returnSlip;
    }

    @Override
    public String toString() {
        return "Return{" +
                "id=" + id +
                ", returnLength=" + returnLength +
                ", returnReason='" + returnReason + '\'' +
                ", money=" + money +
                ", returnSlip=" + returnSlip +
                '}';
    }
}
