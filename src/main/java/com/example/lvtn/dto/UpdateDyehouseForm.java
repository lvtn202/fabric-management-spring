package com.example.lvtn.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateDyehouseForm {
    private Long id;

    private String address;

    private String phoneNumber;

    private String email;

    @Override
    public String toString() {
        return "UpdateDyehouseForm{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
