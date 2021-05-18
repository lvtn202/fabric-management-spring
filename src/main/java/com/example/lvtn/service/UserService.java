package com.example.lvtn.service;

import com.example.lvtn.dom.User;
import com.example.lvtn.dto.LoginForm;
import com.example.lvtn.dto.ResetPasswordForm;
import com.example.lvtn.dto.SignUpForm;
import com.example.lvtn.dto.UserDTO;
import com.example.lvtn.utils.InternalException;
import org.springframework.ui.ModelMap;

import java.util.List;

public interface UserService {
    List<User> findAll();

    boolean isEmailExisted(String email) throws InternalException;

    ModelMap createUser(SignUpForm signUpForm) throws InternalException;

    boolean checkPassword(LoginForm loginForm) throws InternalException;

    ModelMap login(LoginForm loginForm) throws InternalException;

    boolean checkToken(String token) throws InternalException;

    void logout(String token) throws InternalException;

    void sendEmailResetPassword(String email) throws InternalException;

    boolean checkExpiredToken(String token) throws InternalException;

    void resetPassword(ResetPasswordForm resetPasswordForm) throws InternalException;

    List<UserDTO> findUserDTOsWithPaging(Long pageIndex, Long pageSize) throws InternalException;

    boolean checkTokenAdmin(String token) throws InternalException;
}
