package com.example.lvtn.web;


import com.example.lvtn.dto.CreateOrderForm;
import com.example.lvtn.dto.SignUpForm;
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

    @Autowired
    FabricService fabricService;

    @Autowired
    FabricTypeService fabricTypeService;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String main() {
        return "hello";
    }

    @CrossOrigin
    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    @ResponseBody
    public ModelMap signUp(@RequestBody SignUpForm signUpForm) throws InternalException {
        System.out.println("Sign Up: " + signUpForm.toString());

        ModelMap modelMap = new ModelMap();

        if(userService.isEmailExisted(signUpForm.getEmail())){
            modelMap.addAttribute("status", 0);
            modelMap.addAttribute("status_code", "EMAIL_EXISTED");
            return modelMap;
        }

        modelMap.addAttribute("status", 1);
        modelMap.addAttribute("status_code", "OK");
        modelMap.addAttribute("result", userService.createUser(signUpForm));
        return modelMap;
    }

    @CrossOrigin
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ModelMap login(@RequestBody LoginForm loginForm) throws InternalException {
        System.out.println("email: " + loginForm.getEmail());
        System.out.println("password: " + loginForm.getPassword());

        ModelMap modelMap = new ModelMap();

        if(!userService.isEmailExisted(loginForm.getEmail())){
            modelMap.addAttribute("status", 0);
            modelMap.addAttribute("status_code", "EMAIL_NOT_EXISTED");
            return modelMap;
        } else if (!userService.checkPassword(loginForm)){
            modelMap.addAttribute("status", 0);
            modelMap.addAttribute("status_code", "WRONG_PASSWORD");
            return modelMap;
        } else {
            modelMap.addAttribute("status", 1);
            modelMap.addAttribute("status_code", "OK");
            modelMap.addAttribute("result", userService.login(loginForm));
        }
        return modelMap;
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
    @RequestMapping(value = "/detailDyehouse/update", method = RequestMethod.PUT)
    @ResponseBody
    public  ModelMap updateDyehouse(@ModelAttribute UpdateDyehouseForm updateDyehouseForm,
                                    @RequestHeader("token") String token) throws InternalException {
        System.out.println("updateDyehouseForm: " + updateDyehouseForm.toString());
        System.out.println("token: " + token);

        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("status", 1);
        modelMap.addAttribute("status_code", "OK");
        modelMap.addAttribute("result", dyehouseService.updateDyehouse(updateDyehouseForm));
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

    @CrossOrigin
    @RequestMapping(value = "listStatisticRawFabric", method = RequestMethod.GET)
    @ResponseBody
    public ModelMap getListStatisticRawFabric(@RequestParam("dyehouseId") Long dyehouseId,
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
        modelMap.addAttribute("result", fabricService.findStatisticRawFabrics(dyehouseId, pageIndex, pageSize));
        return modelMap;
    }

    @CrossOrigin
    @RequestMapping(value = "listStatisticCompletedFabricInDyehouse", method = RequestMethod.GET)
    @ResponseBody
    public ModelMap getListStatisticCompletedFabricInDyehouse(@RequestParam("dyehouseId") Long dyehouseId,
                                           @RequestParam("startDate") Double startDate,
                                           @RequestParam("endDate") Double endDate,
                                           @RequestParam("pageIndex") Long pageIndex,
                                           @RequestParam("pageSize") Long pageSize,
                                           @RequestHeader("token") String token) throws InternalException {
        System.out.println("dyehouseId: " + dyehouseId);
        System.out.println("startDate: " + startDate);
        System.out.println("endDate: " + endDate);
        System.out.println("pageIndex: " + pageIndex);
        System.out.println("pageSize: " + pageSize);
        System.out.println("token: " + token);

        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("status", 1);
        modelMap.addAttribute("status_code", "OK");
        modelMap.addAttribute("result", fabricService.findStatisticCompletedFabricsInDyehouse(dyehouseId, startDate, endDate, pageIndex, pageSize));
        return modelMap;
    }

    @CrossOrigin
    @RequestMapping(value = "listStatisticExportedFabric", method = RequestMethod.GET)
    @ResponseBody
    public ModelMap getListStatisticExportedFabric(@RequestParam("pageIndex") Long pageIndex,
                                                    @RequestParam("pageSize") Long pageSize,
                                                   @RequestHeader("token") String token) throws InternalException {
        System.out.println("pageIndex: " + pageIndex);
        System.out.println("pageSize: " + pageSize);
        System.out.println("token: " + token);

        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("status", 1);
        modelMap.addAttribute("status_code", "OK");
        modelMap.addAttribute("result", dyehouseService.findStatisticExportedFabrics(pageIndex, pageSize));
        return modelMap;
    }

    @CrossOrigin
    @RequestMapping(value = "listFabricType", method = RequestMethod.GET)
    @ResponseBody
    public ModelMap getListFabricType(@RequestHeader("token") String token) throws InternalException {
        System.out.println("token: " + token);

        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("status", 1);
        modelMap.addAttribute("status_code", "OK");
        modelMap.addAttribute("result", fabricTypeService.findFabricTypes());
        return modelMap;
    }

    @CrossOrigin
    @RequestMapping(value = "listFabricTypeAndColor", method = RequestMethod.GET)
    @ResponseBody
    public ModelMap getListFabricTypeAndColor(@RequestHeader("token") String token) throws InternalException {
        System.out.println("token: " + token);

        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("status", 1);
        modelMap.addAttribute("status_code", "OK");
        modelMap.addAttribute("result", fabricTypeService.findFabricTypesAndColors());
        return modelMap;
    }

    @CrossOrigin
    @RequestMapping(value = "createOrder", method = RequestMethod.POST)
    @ResponseBody
    public ModelMap createOrder(@RequestBody CreateOrderForm createOrderForm,
                                @RequestHeader("token") String token) throws InternalException {
        System.out.println("createOrderForm: " + createOrderForm);
        System.out.println("token: " + token);

        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("status", 1);
        modelMap.addAttribute("status_code", "OK");
        modelMap.addAttribute("result", orderService.createOrder(createOrderForm));
        return modelMap;
    }

    @CrossOrigin
    @RequestMapping(value = "detailDyeBatch", method = RequestMethod.GET)
    @ResponseBody
    public ModelMap getDetailDyeBatch(@RequestParam("dyeBatchId") Long dyeBatchId,
                                      @RequestHeader("token") String token) throws InternalException {
        System.out.println("dyeBatchId: " + dyeBatchId);
        System.out.println("token: " + token);

        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("status", 1);
        modelMap.addAttribute("status_code", "OK");
        modelMap.addAttribute("result", dyeBatchService.findDetailDyeBatchById(dyeBatchId));
        return modelMap;
    }

    @CrossOrigin
    @RequestMapping(value = "listFabricOfDyeBatch", method = RequestMethod.GET)
    @ResponseBody
    public ModelMap getListFabricOfDyeBatch(@RequestParam("dyeBatchId") Long dyeBatchId,
                                      @RequestHeader("token") String token) throws InternalException {
        System.out.println("dyeBatchId: " + dyeBatchId);
        System.out.println("token: " + token);

        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("status", 1);
        modelMap.addAttribute("status_code", "OK");
        modelMap.addAttribute("result", fabricService.findFabricsByDyeBatchId(dyeBatchId));
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