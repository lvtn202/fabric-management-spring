package com.example.lvtn.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailResetPasswordForm {
    private String email;

    public EmailResetPasswordForm() {
    }

    public EmailResetPasswordForm(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "EmailResetPasswordForm{" +
                "email='" + email + '\'' +
                '}';
    }
}
