package com.example.lvtn.service.impl;

import com.example.lvtn.dao.PersistentLoginRepository;
import com.example.lvtn.dao.RoleRepository;
import com.example.lvtn.dao.UserRepository;
import com.example.lvtn.dom.*;
import com.example.lvtn.dto.*;
import com.example.lvtn.service.PersistentLoginService;
import com.example.lvtn.service.UserService;
import com.example.lvtn.utils.GenerateSHA256Password;
import com.example.lvtn.utils.GenerateToken;
import com.example.lvtn.utils.InternalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PersistentLoginRepository persistentLoginRepository;

    @Autowired
    public JavaMailSender emailSender;

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

    @Override
    public boolean checkPassword(LoginForm loginForm) throws InternalException {
        try {
            List<User> listUser = userRepository.findUsersByEmail(loginForm.getEmail());
            User user = listUser.get(0);
            if (user.getPassword().equals(GenerateSHA256Password.hash(loginForm.getPassword()))){
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalException(e.getMessage());
        }
    }

    @Override
    public ModelMap login(LoginForm loginForm) throws InternalException {
        try {
            List<User> listUser = userRepository.findUsersByEmail(loginForm.getEmail());
            User user = listUser.get(0);
            List<String> listRole = new ArrayList<>();
            int i = 0;
            for (Role role: user.getRoles()){
                listRole.add(role.getName());
            }
            String token = GenerateToken.generate();

            if(persistentLoginRepository.isPersistentLoginExisted(user.getId())){
                PersistentLogin currentPersistentLogin = persistentLoginRepository.getCurrentPersistentLogin(user.getId());
                currentPersistentLogin.setToken(token);
                currentPersistentLogin.setLastUpdate(new Timestamp(System.currentTimeMillis()));
                persistentLoginRepository.save(currentPersistentLogin);
            } else {
                persistentLoginRepository.save(new PersistentLogin(user,
                                                                    token,
                                                                    new Timestamp(System.currentTimeMillis())));
            }

            ModelMap modelMap = new ModelMap();
            modelMap.addAttribute("id", user.getId());
            modelMap.addAttribute("firstName", user.getFirstName());
            modelMap.addAttribute("lastName", user.getLastName());
            modelMap.addAttribute("email", user.getEmail());
            modelMap.addAttribute("sex", user.getSex());
            modelMap.addAttribute("roles", listRole);
            modelMap.addAttribute("token", token);
            return modelMap;
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalException(e.getMessage());
        }
    }

    @Override
    public boolean checkToken(String token) throws InternalException {
        try {
            List<PersistentLogin> listPersistentLogin = persistentLoginRepository.findAll();
            for (PersistentLogin persistentLogin: listPersistentLogin){
                if (persistentLogin.getToken() != null && persistentLogin.getToken().equals(token)){
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalException(e.getMessage());
        }
    }

    @Override
    public void logout(String token) throws InternalException {
        try {
            PersistentLogin currentPersistentLogin = persistentLoginRepository.getCurrentPersistentLoginByToken(token);
            currentPersistentLogin.setToken(null);
            persistentLoginRepository.save(currentPersistentLogin);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalException(e.getMessage());
        }
    }

    @Override
    public void sendEmailResetPassword(String email) throws InternalException {
        try {
            List<User> listUser = userRepository.findUsersByEmail(email);
            User user = listUser.get(0);
            String token = GenerateToken.generate();

            if(persistentLoginRepository.isPersistentLoginExisted(user.getId())){
                PersistentLogin currentPersistentLogin = persistentLoginRepository.getCurrentPersistentLogin(user.getId());
                currentPersistentLogin.setToken(token);
                currentPersistentLogin.setLastUpdate(new Timestamp(System.currentTimeMillis()));
                persistentLoginRepository.save(currentPersistentLogin);
            } else {
                persistentLoginRepository.save(new PersistentLogin(
                        user,
                        token,
                        new Timestamp(System.currentTimeMillis())));
            }

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Đổi mật khẩu !");
            message.setText("Xin chào " + user.getLastName() + ",\n\n"
            + "Để thay đổi mật khẩu, vui lòng nhấn vào link dưới đây:\n"
            + "https://fabric-management.herokuapp.com/new-password?"
                    + "email=" + email + "&"
                    + "token=" + token + "\n\n"
            + "Trân trọng !");

            // Send Message!
            this.emailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalException(e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public boolean checkExpiredToken(String token) throws InternalException {
        try {
            List<PersistentLogin> listPersistentLogin = persistentLoginRepository.findAll();
            for (PersistentLogin persistentLogin: listPersistentLogin){
                if (persistentLogin.getToken() != null && persistentLogin.getToken().equals(token)){
                    if((System.currentTimeMillis() - persistentLogin.getLastUpdate().getTime()) < (60000*5) ){
                        return true;
                    } else {
                        return false;
                    }
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalException(e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void resetPassword(ResetPasswordForm resetPasswordForm) throws InternalException {
        try {
            PersistentLogin currentPersistentLogin = persistentLoginRepository.getCurrentPersistentLoginByToken(resetPasswordForm.getToken());
            User currentUser = currentPersistentLogin.getUser();

            currentPersistentLogin.setToken(null);
            currentPersistentLogin.setLastUpdate(new Timestamp(System.currentTimeMillis()));
            currentUser.setPassword(GenerateSHA256Password.hash(resetPasswordForm.getNewPassword()));

            persistentLoginRepository.save(currentPersistentLogin);
            userRepository.save(currentUser);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalException(e.getMessage());
        }
    }

    @Override
    public List<UserDTO> findUserDTOsWithPaging(Long pageIndex, Long pageSize) throws InternalException {
        try {
            List<User> listUser = userRepository.findUsersWithPaging(pageIndex, pageSize);
            List<UserDTO> listUserDTO = new ArrayList<>();
            for (User user: listUser){
                listUserDTO.add(UserDTO.convertUserToUserDTO(user));
            }
            return listUserDTO;
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalException(e.getMessage());
        }
    }

    @Override
    public boolean checkTokenAdmin(String token) throws InternalException {
        try {
            List<PersistentLogin> listPersistentLogin = persistentLoginRepository.findAll();
            for (PersistentLogin persistentLogin: listPersistentLogin){
                if (persistentLogin.getToken() != null && persistentLogin.getToken().equals(token)){
                    Set<Role> setRole = persistentLogin.getUser().getRoles();
                    for (Role role: setRole){
                        if(role.getName().equals("APP_ADMIN")){
                            return true;
                        }
                    }
                    return false;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalException(e.getMessage());
        }
    }
}
