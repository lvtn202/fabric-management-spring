package com.example.lvtn.dom;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "payment")
@Getter
@Setter
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double money;

    @Column(name = "create_date", nullable = false)
    private Timestamp createDate;

    @Column(name = "bank_name", length = 100)
    private String bankName;

    @Column(name = "recipient_name", length = 150)
    private String recipientName;

    @ManyToOne
    @JoinColumn(name = "payment_method_id", nullable = false)
    private PaymentMethod paymentMethod;

    @ManyToOne
    @JoinColumn(name = "dyehouse_id", nullable = false)
    private Dyehouse dyehouse;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Payment() {
    }

    public Payment(Double money, Timestamp createDate, String bankName, String recipientName, PaymentMethod paymentMethod, Dyehouse dyehouse, User user) {
        this.money = money;
        this.createDate = createDate;
        this.bankName = bankName;
        this.recipientName = recipientName;
        this.paymentMethod = paymentMethod;
        this.dyehouse = dyehouse;
        this.user = user;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", money=" + money +
                ", createDate=" + createDate +
                ", bankName='" + bankName + '\'' +
                ", recipientName='" + recipientName + '\'' +
                ", paymentMethod=" + paymentMethod +
                ", dyehouse=" + dyehouse +
                ", user=" + user +
                '}';
    }
}
