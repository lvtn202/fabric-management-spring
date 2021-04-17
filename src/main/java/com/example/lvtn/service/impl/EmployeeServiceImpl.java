package com.example.lvtn.service.impl;

import com.example.lvtn.dao.EmployeeRepository;
import com.example.lvtn.dom.User;
import com.example.lvtn.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<User> findAll() {
        return employeeRepository.findAll();
    }
}
