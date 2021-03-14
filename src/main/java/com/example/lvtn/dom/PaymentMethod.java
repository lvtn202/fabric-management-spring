package com.example.lvtn.dom;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "payment_method")
@Getter
@Setter
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 100, nullable = false)
    private String name;

    @OneToMany(mappedBy = "paymentMethod")
    private Set<Payment> payments;

    public PaymentMethod() {
    }

    public PaymentMethod(Long id, String name, Set<Payment> payments) {
        this.id = id;
        this.name = name;
        this.payments = payments;
    }

    @Override
    public String toString() {
        return "PaymentMethod{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
