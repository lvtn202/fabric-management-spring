package com.example.lvtn.web;


import com.example.lvtn.dao.OrderRepository;
import com.example.lvtn.dom.*;
import com.example.lvtn.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.List;

@Controller
@ControllerAdvice
public class ApplicationController {
    @Autowired
    private EmployeeService employeeService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String main() {
        List<Employee> list =  employeeService.findAll();
        for (int i = 0; i < list.size(); i++){
            System.out.println(list.get(i));
        }

        return "hello";
    }
}