package com.example.lvtn.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenResetPasswordForm {
    private String token;

    public TokenResetPasswordForm() {
    }

    public TokenResetPasswordForm(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "TokenResetPasswordForm{" +
                "token='" + token + '\'' +
                '}';
    }
}
