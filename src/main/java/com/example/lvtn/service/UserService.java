package com.example.lvtn.service;

import com.example.lvtn.dom.User;
import com.example.lvtn.dto.SignUpForm;
import com.example.lvtn.utils.InternalException;
import org.springframework.ui.ModelMap;

import java.util.List;

public interface UserService {
    List<User> findAll();

    boolean isEmailExisted(String email) throws InternalException;

    ModelMap createUser(SignUpForm signUpForm) throws InternalException;
}
