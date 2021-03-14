package com.example.lvtn.service.impl;

import com.example.lvtn.dao.EmployeeRepository;
import com.example.lvtn.dom.Employee;
import com.example.lvtn.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }
}
