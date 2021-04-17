package com.example.lvtn.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpForm {
    private String firstName;

    private String middleName;

    private String lastName;

    private String username;

    private String email;

    private String password;

    private String sex;

    public SignUpForm(String firstName, String middleName, String lastName, String username, String email, String password, String sex) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "SignUpForm{" +
                "firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }
}
