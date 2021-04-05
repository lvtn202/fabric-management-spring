package com.example.lvtn.dto;

import com.example.lvtn.dom.Order;
import com.example.lvtn.utils.OrderStatus;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDTO {
    private Long id;

    private String type;

    private String color;

    private OrderStatus status;

    private String createDate;

    private String orderLength;

    private String doneLength;

    public OrderDTO(Long id, String type, String color, OrderStatus status, String createDate, String orderLength, String doneLength) {
        this.id = id;
        this.type = type;
        this.color = color;
        this.status = status;
        this.createDate = createDate;
        this.orderLength = orderLength;
        this.doneLength = doneLength;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", color='" + color + '\'' +
                ", status=" + status +
                ", createDate=" + createDate +
                ", orderLength='" + orderLength + '\'' +
                ", doneLength='" + doneLength + '\'' +
                '}';
    }

    static public OrderDTO convertOrderToOrderDTO (Order order){
        return new OrderDTO(
                order.getId(),
                order.getColor().getFabricType().getType(),
                order.getColor().getName(),
                order.getStatus(),
                order.getCreateDate().toString(),
                String.format("%.1f", order.getOrderLength()),
                String.format("%.1f", order.getDoneLength())
        );
    }
}
