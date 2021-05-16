package com.example.lvtn.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateDyehouseForm {
    private String name;

    private String address;

    private String phoneNumber;

    private String email;

    public CreateDyehouseForm() {
    }

    public CreateDyehouseForm(String name, String address, String phoneNumber, String email) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    @Override
    public String toString() {
        return "CreateDyehouseForm{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
