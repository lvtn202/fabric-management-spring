package com.example.lvtn.web;


import com.example.lvtn.dto.UpdateDyehouseForm;
import com.example.lvtn.service.*;
import com.example.lvtn.utils.InternalException;
import com.example.lvtn.dto.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@ControllerAdvice
public class ApplicationController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private DyehouseService dyehouseService;

    @Autowired
    private ImportSlipService importSlipService;

    @Autowired
    private DyeBatchService dyeBatchService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String main() {
        return "hello";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestBody LoginForm loginForm) {
        System.out.println("username: " + loginForm.getUsername());
        System.out.println("password: " + loginForm.getPassword());

        return "hello";
    }

    @CrossOrigin
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

        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("status", 1);
        modelMap.addAttribute("status_code", "OK");
        modelMap.addAttribute("result", dyehouseService.findDyehouseDTOsByNameWithPaging(dyehouseName, pageIndex, pageSize));
        return modelMap;
    }

    @CrossOrigin
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

    @CrossOrigin
    @RequestMapping(value = "/listOrder", method = RequestMethod.GET)
    @ResponseBody
    public ModelMap getListOrder(@RequestParam("dyehouseId") Long dyehouseId,
                                 @RequestParam("pageIndex") Long pageIndex,
                                 @RequestParam("pageSize") Long pageSize,
                                 @RequestHeader("token") String token) throws InternalException {
        System.out.println("dyehouseId: " + dyehouseId);
        System.out.println("pageIndex: " + pageIndex);
        System.out.println("pageSize: " + pageSize);
        System.out.println("token: " + token);

        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("status", 1);
        modelMap.addAttribute("status_code", "OK");
        modelMap.addAttribute("result", orderService.findOrderDTOsByDyehouseIdWithPaging(dyehouseId, pageIndex, pageSize));
        return modelMap;
    }

    @CrossOrigin
    @RequestMapping(value = "/detailDyehouse/update", method = RequestMethod.POST)
    @ResponseBody
    public  ModelMap updateDyehouse(@ModelAttribute UpdateDyehouseForm updateDyehouseForm,
                                    @RequestHeader("token") String token) throws InternalException {
        System.out.println("updateDyehouseForm: " + updateDyehouseForm.toString());
        System.out.println("token: " + token);

        dyehouseService.updateDyehouse(updateDyehouseForm);

        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("status", 1);
        modelMap.addAttribute("status_code", "OK");
        return modelMap;
    }

    @CrossOrigin
    @RequestMapping(value = "/detailOrder", method = RequestMethod.GET)
    @ResponseBody
    public ModelMap getDetailOrder(@RequestParam("id") Long id,
                                   @RequestHeader("token") String token) throws InternalException {
        System.out.println("id: " + id);
        System.out.println("token: " + token);

        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("status", 1);
        modelMap.addAttribute("status_code", "OK");
        modelMap.addAttribute("result", orderService.findDetailOrderById(id));
        return modelMap;
    }

    @CrossOrigin
    @RequestMapping(value = "/listImportSlipOfOrder", method = RequestMethod.GET)
    @ResponseBody
    public ModelMap getListImportSlipOfOrder(@RequestParam("orderId") Long orderId,
                                       @RequestParam("pageIndex") Long pageIndex,
                                       @RequestParam("pageSize") Long pageSize,
                                       @RequestHeader("token") String token) throws InternalException {
        System.out.println("orderId: " + orderId);
        System.out.println("pageIndex: " + pageIndex);
        System.out.println("pageSize: " + pageSize);
        System.out.println("token: " + token);

        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("status", 1);
        modelMap.addAttribute("status_code", "OK");
        modelMap.addAttribute("result", importSlipService.findImportSlipDTOsByOrderIdWithPaging(orderId, pageIndex, pageSize));
        return modelMap;
    }

    @CrossOrigin
    @RequestMapping(value = "/listDyeBatch", method = RequestMethod.GET)
    @ResponseBody
    public ModelMap getListDyeBatch(@RequestParam("importSlipId") Long importSlipId,
                                             @RequestParam("pageIndex") Long pageIndex,
                                             @RequestParam("pageSize") Long pageSize,
                                             @RequestHeader("token") String token) throws InternalException {
        System.out.println("importSlipId: " + importSlipId);
        System.out.println("pageIndex: " + pageIndex);
        System.out.println("pageSize: " + pageSize);
        System.out.println("token: " + token);

        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("status", 1);
        modelMap.addAttribute("status_code", "OK");
        modelMap.addAttribute("result", dyeBatchService.findDyeBatches(importSlipId, pageIndex, pageSize));
        return modelMap;
    }

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