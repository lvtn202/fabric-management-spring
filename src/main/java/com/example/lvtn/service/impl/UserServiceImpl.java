package com.example.lvtn.service.impl;

import com.example.lvtn.dao.RoleRepository;
import com.example.lvtn.dao.UserRepository;
import com.example.lvtn.dom.*;
import com.example.lvtn.dto.SignUpForm;
import com.example.lvtn.service.UserService;
import com.example.lvtn.utils.GenerateSHA256Password;
import com.example.lvtn.utils.InternalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.*;

@Service
public class
UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public boolean isEmailExisted(String email) throws InternalException {
        try {
            List<User> listUser = userRepository.findUsersByEmail(email);
            if (listUser.size() > 0){
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalException(e.getMessage());
        }
    }

    @Override
    public ModelMap createUser(SignUpForm signUpForm) throws InternalException {
        try {
            Set<Role> roles = new HashSet<>();
            roles.add(roleRepository.findRoleByName("APP_USER"));
            User newUser = new User(
                    signUpForm.getFirstName(),
                    signUpForm.getLastName(),
                    signUpForm.getEmail(),
                    GenerateSHA256Password.hash(signUpForm.getPassword()),
                    signUpForm.getSex(),
                    new HashSet<PersistentLogin>(),
                    new HashSet<ImportSlip>(),
                    new HashSet<ExportSlip>(),
                    new HashSet<Payment>(),
                    new HashSet<ReturnSlip>(),
                    roles
            );

            userRepository.save(newUser);

            ModelMap modelMap = new ModelMap();
            modelMap.addAttribute("id", newUser.getId());
            modelMap.addAttribute("firstName", newUser.getFirstName());
            modelMap.addAttribute("lastName", newUser.getLastName());
            modelMap.addAttribute("email", newUser.getEmail());
            modelMap.addAttribute("sex", newUser.getSex());
            modelMap.addAttribute("roles", "APP_USER");
            return modelMap;
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalException(e.getMessage());
        }
    }
}
