package com.example.lvtn.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginForm {
    private String email;

    private String password;



    @Override
    public String toString() {
        return "LoginForm{" +
                "username='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
