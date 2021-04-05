package com.example.lvtn.web;


import com.example.lvtn.dom.Dyehouse;
import com.example.lvtn.dto.DyehouseDTO;
import com.example.lvtn.service.*;
import com.example.lvtn.utils.InternalException;
import com.example.lvtn.utils.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@ControllerAdvice
public class ApplicationController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private DyehouseService dyehouseService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String main() {
//        List<Order> employeeList = orderService.findAll();
//        for (int i = 0; i <employeeList.size(); i++){
//            System.out.println(employeeList.get(i).toString());
//        }
        return "hello";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestBody LoginForm loginForm) {
        System.out.println("username: " + loginForm.getUsername());
        System.out.println("password: " + loginForm.getPassword());

        return "hello";
    }

    @RequestMapping(value = "/listDyehouse", method = RequestMethod.GET)
    @ResponseBody
    public ModelMap getListDyehouse(@RequestParam("dyehouseName") String dyehouseName,
                                @RequestParam("pageIndex") Long pageIndex,
                                @RequestParam("pageSize") Long pageSize,
                                @RequestHeader("token") String token) throws InternalException {

        System.out.println("dyehouseName: " + dyehouseName);
        System.out.println("pageIndex: " + pageIndex);
        System.out.println("pageSize: " + pageSize);
        System.out.println("token: " + token);

        List<DyehouseDTO> dyehouseDTOList = dyehouseService.findDyehouseDTOsWithNameAndPaging(dyehouseName, pageIndex, pageSize);
        for (int i = 0; i <dyehouseDTOList.size(); i++){
            System.out.println(dyehouseDTOList.get(i).toString());
        }

        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("status", 1);
        modelMap.addAttribute("status_code", "OK");
        modelMap.addAttribute("result", dyehouseService.findDyehouseDTOsWithNameAndPaging(dyehouseName, pageIndex, pageSize));
        return modelMap;
    }

    @RequestMapping(value = "/detailDyehouse", method = RequestMethod.GET)
    @ResponseBody
    public ModelMap getDetailDyehouse(@RequestParam("id") Long id,
                                      @RequestHeader("token") String token) throws InternalException {
        System.out.println("id: " + id);
        System.out.println("token: " + token);

        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("status", 1);
        modelMap.addAttribute("status_code", "OK");
        modelMap.addAttribute("result", dyehouseService.findDetailDyehouseById(id));
        return modelMap;
    }

//    @RequestMapping(value = "/listOrder", method = RequestMethod.GET)
//    @ResponseBody
//    public ModelMap getListOrder()

    @ExceptionHandler(InternalException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ModelMap handleError(InternalException internalException){
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("status", 0);
        modelMap.addAttribute("status_code", internalException.getMessage());
        modelMap.addAttribute("result", null);
        return modelMap;
    }
}