package com.example.lvtn.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordForm {
    private String email;

    private String newPassword;

    private String token;

    public ResetPasswordForm() {
    }

    public ResetPasswordForm(String email, String newPassword, String token) {
        this.email = email;
        this.newPassword = newPassword;
        this.token = token;
    }

    @Override
    public String toString() {
        return "ResetPasswordForm{" +
                "email='" + email + '\'' +
                ", newPassword='" + newPassword + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
