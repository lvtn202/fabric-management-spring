package com.example.lvtn.dom;

import com.example.lvtn.utils.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "create_date", nullable = false)
    private Timestamp createDate;

    @Column(name = "order_length", nullable = false)
    private Double orderLength;

    @Column(name = "done_length", nullable = false)
    private Double doneLength;

    @ManyToOne
    @JoinColumn(name = "dyehouse_id", nullable = false)
    private Dyehouse dyehouse;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "color_id", nullable = false)
    private Color color;

    @OneToMany(mappedBy = "order")
    Set<ImportSlip> importSlips;

    public Order() {
    }

    public Order(OrderStatus status, Timestamp createDate, Double orderLength, Double doneLength, Dyehouse dyehouse, User user, Color color, Set<ImportSlip> importSlips) {
        this.status = status;
        this.createDate = createDate;
        this.orderLength = orderLength;
        this.doneLength = doneLength;
        this.dyehouse = dyehouse;
        this.user = user;
        this.color = color;
        this.importSlips = importSlips;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", status=" + status +
                ", createDate=" + createDate +
                ", orderLength=" + orderLength +
                ", doneLength=" + doneLength +
                ", dyehouse=" + dyehouse +
                ", user=" + user +
                ", color=" + color +
                '}';
    }
}
