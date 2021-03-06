package com.example.lvtn.web;


import com.example.lvtn.dto.*;
import com.example.lvtn.service.*;
import com.example.lvtn.utils.InternalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

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
    private FabricService fabricService;

    @Autowired
    private FabricTypeService fabricTypeService;

    @Autowired
    private UserService userService;

    @Autowired
    private ReturnSlipService returnSlipService;

    @Autowired
    private ReturnService returnService;

    @Autowired
    private ExportSlipService exportSlipService;

    @Autowired
    private ColorService colorService;

    @Autowired
    private PaymentMethodService paymentMethodService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private DebtService debtService;

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
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @ResponseBody
    public ModelMap logout(@RequestHeader("token") String token) throws InternalException {
        System.out.println("token: " + token);

        ModelMap modelMap = new ModelMap();
        if (!userService.checkToken(token)){
            modelMap.addAttribute("status", 0);
            modelMap.addAttribute("status_code", "ERROR_TOKEN");
            return modelMap;
        }
        userService.logout(token);
        modelMap.addAttribute("status", 1);
        modelMap.addAttribute("status_code", "OK");
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
        if (!userService.checkToken(token)){
            modelMap.addAttribute("status", 0);
            modelMap.addAttribute("status_code", "ERROR_TOKEN");
            return modelMap;
        }

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
        if (!userService.checkToken(token)){
            modelMap.addAttribute("status", 0);
            modelMap.addAttribute("status_code", "ERROR_TOKEN");
            return modelMap;
        }

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
        if (!userService.checkToken(token)){
            modelMap.addAttribute("status", 0);
            modelMap.addAttribute("status_code", "ERROR_TOKEN");
            return modelMap;
        }

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
        if (!userService.checkToken(token)){
            modelMap.addAttribute("status", 0);
            modelMap.addAttribute("status_code", "ERROR_TOKEN");
            return modelMap;
        }

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
        if (!userService.checkToken(token)){
            modelMap.addAttribute("status", 0);
            modelMap.addAttribute("status_code", "ERROR_TOKEN");
            return modelMap;
        }

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
        if (!userService.checkToken(token)){
            modelMap.addAttribute("status", 0);
            modelMap.addAttribute("status_code", "ERROR_TOKEN");
            return modelMap;
        }

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
        if (!userService.checkToken(token)){
            modelMap.addAttribute("status", 0);
            modelMap.addAttribute("status_code", "ERROR_TOKEN");
            return modelMap;
        }

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
        if (!userService.checkToken(token)){
            modelMap.addAttribute("status", 0);
            modelMap.addAttribute("status_code", "ERROR_TOKEN");
            return modelMap;
        }

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
        if (!userService.checkToken(token)){
            modelMap.addAttribute("status", 0);
            modelMap.addAttribute("status_code", "ERROR_TOKEN");
            return modelMap;
        }

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
        if (!userService.checkToken(token)){
            modelMap.addAttribute("status", 0);
            modelMap.addAttribute("status_code", "ERROR_TOKEN");
            return modelMap;
        }

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
        if (!userService.checkToken(token)){
            modelMap.addAttribute("status", 0);
            modelMap.addAttribute("status_code", "ERROR_TOKEN");
            return modelMap;
        }

        modelMap.addAttribute("status", 1);
        modelMap.addAttribute("status_code", "OK");
        modelMap.addAttribute("result", fabricTypeService.findFabricTypes());
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
        if (!userService.checkToken(token)){
            modelMap.addAttribute("status", 0);
            modelMap.addAttribute("status_code", "ERROR_TOKEN");
            return modelMap;
        }

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
        if (!userService.checkToken(token)){
            modelMap.addAttribute("status", 0);
            modelMap.addAttribute("status_code", "ERROR_TOKEN");
            return modelMap;
        }

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
        if (!userService.checkToken(token)){
            modelMap.addAttribute("status", 0);
            modelMap.addAttribute("status_code", "ERROR_TOKEN");
            return modelMap;
        }

        modelMap.addAttribute("status", 1);
        modelMap.addAttribute("status_code", "OK");
        modelMap.addAttribute("result", fabricService.findFabricsByDyeBatchId(dyeBatchId));
        return modelMap;
    }

    @CrossOrigin
    @RequestMapping(value = "getRawLength", method = RequestMethod.GET)
    @ResponseBody
    public ModelMap getRawLength(@RequestParam("fabricId") Long fabricId,
                               @RequestHeader("token") String token) throws InternalException {
        System.out.println("fabricId: " + fabricId);
        System.out.println("token: " + token);
        ModelMap modelMap = new ModelMap();

        if (!userService.checkToken(token)){
            modelMap.addAttribute("status", 0);
            modelMap.addAttribute("status_code", "ERROR_TOKEN");
            return modelMap;
        }

        if(!fabricService.isRawFabric(fabricId)){
            modelMap.addAttribute("status", 0);
            modelMap.addAttribute("status_code", "NOT_RAW_FABRIC");
            return modelMap;
        }

        modelMap.addAttribute("status", 1);
        modelMap.addAttribute("status_code", "OK");
        modelMap.addAttribute("result", fabricService.getRawLength(fabricId));
        return modelMap;
    }

    @CrossOrigin
    @RequestMapping(value = "listReturnSlip", method = RequestMethod.GET)
    @ResponseBody
    public ModelMap getListReturnSlip(@RequestParam("pageIndex") Long pageIndex,
                                      @RequestParam("pageSize") Long pageSize,
                                      @RequestHeader("token") String token) throws InternalException {
        System.out.println("pageIndex: " + pageIndex);
        System.out.println("pageSize: " + pageSize);
        System.out.println("token: " + token);

        ModelMap modelMap = new ModelMap();
        if (!userService.checkToken(token)){
            modelMap.addAttribute("status", 0);
            modelMap.addAttribute("status_code", "ERROR_TOKEN");
            return modelMap;
        }

        modelMap.addAttribute("status", 1);
        modelMap.addAttribute("status_code", "OK");
        modelMap.addAttribute("result", returnSlipService.findReturnSlipDTOsWithPaging(pageIndex, pageSize));
        return modelMap;
    }

    @CrossOrigin
    @RequestMapping(value = "detailReturnSlip", method = RequestMethod.GET)
    @ResponseBody
    public ModelMap getDetailReturnSlip(@RequestParam("returnSlipId") Long returnSlipId,
                                        @RequestHeader("token") String token) throws InternalException {
        System.out.println("returnSlipId: " + returnSlipId);
        System.out.println("token: " + token);

        ModelMap modelMap = new ModelMap();
        if (!userService.checkToken(token)){
            modelMap.addAttribute("status", 0);
            modelMap.addAttribute("status_code", "ERROR_TOKEN");
            return modelMap;
        }

        modelMap.addAttribute("status", 1);
        modelMap.addAttribute("status_code", "OK");
        modelMap.addAttribute("result", returnSlipService.findDetailReturnSlip(returnSlipId));
        return modelMap;
    }

    @CrossOrigin
    @RequestMapping(value = "listReturn", method = RequestMethod.GET)
    @ResponseBody
    public ModelMap getListReturn(@RequestParam("returnSlipId") Long returnSlipId,
                                  @RequestParam("pageIndex") Long pageIndex,
                                  @RequestParam("pageSize") Long pageSize,
                                  @RequestHeader("token") String token) throws InternalException {
        System.out.println("returnSlipId: " + returnSlipId);
        System.out.println("pageIndex: " + pageIndex);
        System.out.println("pageSize: " + pageSize);
        System.out.println("token: " + token);

        ModelMap modelMap = new ModelMap();
        if (!userService.checkToken(token)){
            modelMap.addAttribute("status", 0);
            modelMap.addAttribute("status_code", "ERROR_TOKEN");
            return modelMap;
        }

        modelMap.addAttribute("status", 1);
        modelMap.addAttribute("status_code", "OK");
        modelMap.addAttribute("result", returnService.findReturnDTOs(returnSlipId, pageIndex, pageSize));
        return modelMap;
    }

    @CrossOrigin
    @RequestMapping(value = "detailReturn", method = RequestMethod.GET)
    @ResponseBody
    public ModelMap getDetailReturn(@RequestParam("returnId") Long returnId,
                                    @RequestHeader("token") String token) throws InternalException {
        System.out.println("returnId: " + returnId);
        System.out.println("token: " + token);

        ModelMap modelMap = new ModelMap();
        if (!userService.checkToken(token)){
            modelMap.addAttribute("status", 0);
            modelMap.addAttribute("status_code", "ERROR_TOKEN");
            return modelMap;
        }

        modelMap.addAttribute("status", 1);
        modelMap.addAttribute("status_code", "OK");
        modelMap.addAttribute("result", returnService.findDetailReturn(returnId));
        return modelMap;
    }

    @CrossOrigin
    @RequestMapping(value = "listDebt", method = RequestMethod.GET)
    @ResponseBody
    public ModelMap getListDebt(@RequestParam("pageIndex") Long pageIndex,
                                @RequestParam("pageSize") Long pageSize,
                                @RequestHeader("token") String token) throws InternalException {
        System.out.println("pageIndex: " + pageIndex);
        System.out.println("pageSize: " + pageSize);
        System.out.println("token: " + token);

        ModelMap modelMap = new ModelMap();
        if (!userService.checkToken(token)){
            modelMap.addAttribute("status", 0);
            modelMap.addAttribute("status_code", "ERROR_TOKEN");
            return modelMap;
        }

        modelMap.addAttribute("status", 1);
        modelMap.addAttribute("status_code", "OK");
        modelMap.addAttribute("result", dyehouseService.findDebtsWithPaging(pageIndex, pageSize));
        return modelMap;
    }

    @CrossOrigin
    @RequestMapping(value = "listRawFabric", method = RequestMethod.GET)
    @ResponseBody
    public ModelMap getListRawFabric(@RequestParam("fabricType") String fabricType,
                                     @RequestHeader("token") String token) throws InternalException {
        System.out.println("fabricType: " + fabricType);
        System.out.println("token: " + token);

        ModelMap modelMap = new ModelMap();
        if (!userService.checkToken(token)){
            modelMap.addAttribute("status", 0);
            modelMap.addAttribute("status_code", "ERROR_TOKEN");
            return modelMap;
        }

        modelMap.addAttribute("status", 1);
        modelMap.addAttribute("status_code", "OK");
        modelMap.addAttribute("result", fabricService.findRawFabricDTOsByFabricType(fabricType));
        return modelMap;
    }

    @CrossOrigin
    @RequestMapping(value = "listExportedFabric", method = RequestMethod.GET)
    @ResponseBody
    public ModelMap getListExportedFabric(@RequestParam("dyehouseId") Long dyehouseId,
                                          @RequestParam("fabricType") String fabricType,
                                          @RequestHeader("token") String token) throws InternalException {
        System.out.println("dyehouseId: " + dyehouseId);
        System.out.println("fabricType: " + fabricType);
        System.out.println("token: " + token);

        ModelMap modelMap = new ModelMap();
        if (!userService.checkToken(token)){
            modelMap.addAttribute("status", 0);
            modelMap.addAttribute("status_code", "ERROR_TOKEN");
            return modelMap;
        }

        modelMap.addAttribute("status", 1);
        modelMap.addAttribute("status_code", "OK");
        modelMap.addAttribute("result", fabricService.findExportedFabricDTOsByFabricType(dyehouseId, fabricType));
        return modelMap;
    }

    @CrossOrigin
    @RequestMapping(value = "createExportSlip", method = RequestMethod.POST)
    @ResponseBody
    public ModelMap createExportSlip(@RequestBody CreateExportSlipForm createExportSlipForm,
                                     @RequestHeader("token") String token) throws InternalException {
        System.out.println("createExportSlipForm: " + createExportSlipForm.toString());
        System.out.println("token: " + token);

        ModelMap modelMap = new ModelMap();
        if (!userService.checkToken(token)){
            modelMap.addAttribute("status", 0);
            modelMap.addAttribute("status_code", "ERROR_TOKEN");
            return modelMap;
        }

        modelMap.addAttribute("status", 1);
        modelMap.addAttribute("status_code", "OK");
        modelMap.addAttribute("result", exportSlipService.createExportSlip(createExportSlipForm));
        return modelMap;
    }

    @CrossOrigin
    @RequestMapping(value = "listOrderAdvance", method = RequestMethod.GET)
    @ResponseBody
    public ModelMap getListOrderAdvance(@RequestParam("dyehouseId") Long dyehouseId,
                                        @RequestParam("fabricType") String fabricType,
                                        @RequestParam("color") String color,
                                        @RequestHeader("token") String token) throws InternalException {
        System.out.println("dyehouseId: " + dyehouseId);
        System.out.println("fabricType: " + fabricType);
        System.out.println("color: " + color);
        System.out.println("token: " + token);

        ModelMap modelMap = new ModelMap();
        if (!userService.checkToken(token)){
            modelMap.addAttribute("status", 0);
            modelMap.addAttribute("status_code", "ERROR_TOKEN");
            return modelMap;
        }

        modelMap.addAttribute("status", 1);
        modelMap.addAttribute("status_code", "OK");
        modelMap.addAttribute("result", orderService.findOrderDTOsByFabricTypeAndColor(dyehouseId, fabricType, color));
        return modelMap;
    }

    @CrossOrigin
    @RequestMapping(value = "getPrice", method = RequestMethod.GET)
    @ResponseBody
    public ModelMap getPrice(@RequestParam("fabricType") String fabricType,
                             @RequestParam("color") String color,
                             @RequestHeader("token") String token) throws InternalException {
        System.out.println("fabricType: " + fabricType);
        System.out.println("color: " + color);
        System.out.println("token: " + token);

        ModelMap modelMap = new ModelMap();
        if (!userService.checkToken(token)){
            modelMap.addAttribute("status", 0);
            modelMap.addAttribute("status_code", "ERROR_TOKEN");
            return modelMap;
        }

        modelMap.addAttribute("status", 1);
        modelMap.addAttribute("status_code", "OK");
        modelMap.addAttribute("result", colorService.getPriceByFabricTypeAndColor(fabricType, color));
        return modelMap;
    }

    @CrossOrigin
    @RequestMapping(value = "createImportSlip", method = RequestMethod.POST)
    @ResponseBody
    public ModelMap createImportSlip(@RequestBody CreateImportSlipForm createImportSlipForm,
                                     @RequestHeader("token") String token) throws InternalException {
        System.out.println("createImportSlipForm: " + createImportSlipForm.toString());
        System.out.println("token: " + token);

        ModelMap modelMap = new ModelMap();
        if (!userService.checkToken(token)){
            modelMap.addAttribute("status", 0);
            modelMap.addAttribute("status_code", "ERROR_TOKEN");
            return modelMap;
        }

        modelMap.addAttribute("status", 1);
        modelMap.addAttribute("status_code", "OK");
        modelMap.addAttribute("result", importSlipService.createImportSlip(createImportSlipForm));
        return modelMap;
    }

    @Autowired
    public JavaMailSender emailSender;
    @CrossOrigin
    @RequestMapping(value = "createData", method = RequestMethod.GET)
    @ResponseBody
    public ModelMap createData() throws InternalException {
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("status", 1);
        modelMap.addAttribute("status_code", "OK");
//        modelMap.addAttribute("result", fabricService.createData());
//        modelMap.addAttribute("result", debtService.createData());

//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo("nguyenxuanhuy225@gmail.com");
//        message.setSubject("Test Simple Email");
//        message.setText("Hi Tinh , I'm Huy dz");

//        Send Message!
//        this.emailSender.send(message);
        return modelMap;
    }

    @CrossOrigin
    @RequestMapping(value = "listCompletedFabric", method = RequestMethod.GET)
    @ResponseBody
    public ModelMap getListCompletedFabric(@RequestParam("dyehouseId") Long dyehouseId,
                                           @RequestParam("pageIndex") Long pageIndex,
                                           @RequestParam("pageSize") Long pageSize,
                                           @RequestHeader("token") String token) throws InternalException {
        System.out.println("dyehouseId: " + dyehouseId);
        System.out.println("pageIndex: " + pageIndex);
        System.out.println("pageSize: " + pageSize);
        System.out.println("token: " + token);

        ModelMap modelMap = new ModelMap();
        if (!userService.checkToken(token)){
            modelMap.addAttribute("status", 0);
            modelMap.addAttribute("status_code", "ERROR_TOKEN");
            return modelMap;
        }

        modelMap.addAttribute("status", 1);
        modelMap.addAttribute("status_code", "OK");
        modelMap.addAttribute("result", fabricService.findCompletedFabricDTOs(dyehouseId, pageIndex, pageSize));
        return modelMap;
    }

    @CrossOrigin
    @RequestMapping(value = "createReturnSlip", method = RequestMethod.POST)
    @ResponseBody
    public ModelMap createReturnSlip(@RequestBody CreateReturnSlipForm createReturnSlipForm,
                                     @RequestHeader("token") String token) throws InternalException {
        System.out.println("createImportSlipForm: " + createReturnSlipForm.toString());
        System.out.println("token: " + token);

        ModelMap modelMap = new ModelMap();
        if (!userService.checkToken(token)){
            modelMap.addAttribute("status", 0);
            modelMap.addAttribute("status_code", "ERROR_TOKEN");
            return modelMap;
        }

        modelMap.addAttribute("status", 1);
        modelMap.addAttribute("status_code", "OK");
        modelMap.addAttribute("result", returnSlipService.createReturnSlip(createReturnSlipForm));
        return modelMap;
    }

    @CrossOrigin
    @RequestMapping(value = "listPaymentMethod", method = RequestMethod.GET)
    @ResponseBody
    public ModelMap getListPaymentMethod(@RequestHeader("token") String token) throws InternalException {
        System.out.println("token: " + token);

        ModelMap modelMap = new ModelMap();
        if (!userService.checkToken(token)){
            modelMap.addAttribute("status", 0);
            modelMap.addAttribute("status_code", "ERROR_TOKEN");
            return modelMap;
        }

        modelMap.addAttribute("status", 1);
        modelMap.addAttribute("status_code", "OK");
        modelMap.addAttribute("result", paymentMethodService.findPaymentMethodDTOs());
        return modelMap;
    }

    @CrossOrigin
    @RequestMapping(value = "listPayment", method = RequestMethod.GET)
    @ResponseBody
    public ModelMap getListPayment(@RequestParam("dyehouseId") Long dyehouseId,
                                   @RequestParam("pageIndex") Long pageIndex,
                                   @RequestParam("pageSize") Long pageSize,
                                   @RequestHeader("token") String token) throws InternalException {
        System.out.println("dyehouseId: " + dyehouseId);
        System.out.println("pageIndex: " + pageIndex);
        System.out.println("pageSize: " + pageSize);
        System.out.println("token: " + token);

        ModelMap modelMap = new ModelMap();
        if (!userService.checkToken(token)){
            modelMap.addAttribute("status", 0);
            modelMap.addAttribute("status_code", "ERROR_TOKEN");
            return modelMap;
        }

        modelMap.addAttribute("status", 1);
        modelMap.addAttribute("status_code", "OK");
        modelMap.addAttribute("result", paymentService.findPaymentDTOs(dyehouseId, pageIndex, pageSize));
        return modelMap;
    }

    @CrossOrigin
    @RequestMapping(value = "detailPayment", method = RequestMethod.GET)
    @ResponseBody
    public ModelMap getDetailPayment(@RequestParam("paymentId") Long paymentId,
                                     @RequestHeader("token") String token) throws InternalException {
        System.out.println("paymentId: " + paymentId);
        System.out.println("token: " + token);

        ModelMap modelMap = new ModelMap();
        if (!userService.checkToken(token)){
            modelMap.addAttribute("status", 0);
            modelMap.addAttribute("status_code", "ERROR_TOKEN");
            return modelMap;
        }

        modelMap.addAttribute("status", 1);
        modelMap.addAttribute("status_code", "OK");
        modelMap.addAttribute("result", paymentService.findDetailPaymentDTOByPaymentId(paymentId));
        return modelMap;
    }

    @CrossOrigin
    @RequestMapping(value = "createPayment", method = RequestMethod.POST)
    @ResponseBody
    public ModelMap createPayment(@RequestBody CreatePaymentForm createPaymentForm,
                                  @RequestHeader("token") String token) throws InternalException {
        System.out.println("createImportSlipForm: " + createPaymentForm.toString());
        System.out.println("token: " + token);

        ModelMap modelMap = new ModelMap();
        if (!userService.checkToken(token)){
            modelMap.addAttribute("status", 0);
            modelMap.addAttribute("status_code", "ERROR_TOKEN");
            return modelMap;
        }

        modelMap.addAttribute("status", 1);
        modelMap.addAttribute("status_code", "OK");
        modelMap.addAttribute("result", paymentService.createPayment(createPaymentForm));
        return modelMap;
    }

    @CrossOrigin
    @RequestMapping(value = "totalRecentPayment", method = RequestMethod.GET)
    @ResponseBody
    public ModelMap getTotalRecentPayment(@RequestParam("dyehouseId") Long dyehouseId,
                                          @RequestParam("period") Long period,
                                          @RequestHeader("token") String token) throws InternalException {
        System.out.println("period: " + period);
        System.out.println("dyehouseId: " + dyehouseId);
        System.out.println("token: " + token);

        ModelMap modelMap = new ModelMap();
        if (!userService.checkToken(token)){
            modelMap.addAttribute("status", 0);
            modelMap.addAttribute("status_code", "ERROR_TOKEN");
            return modelMap;
        }

        modelMap.addAttribute("status", 1);
        modelMap.addAttribute("status_code", "OK");
        modelMap.addAttribute("result", paymentService.findTotalRecentPayment(dyehouseId, period));
        return modelMap;
    }

    @CrossOrigin
    @RequestMapping(value = "listRecentImportSlip", method = RequestMethod.GET)
    @ResponseBody
    public ModelMap getListRecentImportSlip(@RequestParam("dyehouseId") Long dyehouseId,
                                            @RequestParam("pageIndex") Long pageIndex,
                                            @RequestParam("pageSize") Long pageSize,
                                            @RequestHeader("token") String token) throws InternalException {
        System.out.println("dyehouseId: " + dyehouseId);
        System.out.println("pageIndex: " + pageIndex);
        System.out.println("pageSize: " + pageSize);
        System.out.println("token: " + token);

        ModelMap modelMap = new ModelMap();
        if (!userService.checkToken(token)){
            modelMap.addAttribute("status", 0);
            modelMap.addAttribute("status_code", "ERROR_TOKEN");
            return modelMap;
        }

        modelMap.addAttribute("status", 1);
        modelMap.addAttribute("status_code", "OK");
        modelMap.addAttribute("result", importSlipService.findRecentImportSlipDTOs(dyehouseId, pageIndex, pageSize));
        return modelMap;
    }

    @CrossOrigin
    @RequestMapping(value = "listRecentExportSlip", method = RequestMethod.GET)
    @ResponseBody
    public ModelMap getListRecentExportSlip(@RequestParam("dyehouseId") Long dyehouseId,
                                            @RequestParam("pageIndex") Long pageIndex,
                                            @RequestParam("pageSize") Long pageSize,
                                            @RequestHeader("token") String token) throws InternalException {
        System.out.println("dyehouseId: " + dyehouseId);
        System.out.println("pageIndex: " + pageIndex);
        System.out.println("pageSize: " + pageSize);
        System.out.println("token: " + token);

        ModelMap modelMap = new ModelMap();
        if (!userService.checkToken(token)){
            modelMap.addAttribute("status", 0);
            modelMap.addAttribute("status_code", "ERROR_TOKEN");
            return modelMap;
        }

        modelMap.addAttribute("status", 1);
        modelMap.addAttribute("status_code", "OK");
        modelMap.addAttribute("result", exportSlipService.findRecentExportSlipDTOs(dyehouseId, pageIndex, pageSize));
        return modelMap;
    }

    @CrossOrigin
    @RequestMapping(value = "getStatisticFabric", method = RequestMethod.GET)
    @ResponseBody
    public ModelMap getStatisticFabric(@RequestParam("dyehouseId") Long dyehouseId,
                                       @RequestHeader("token") String token) throws InternalException {
        System.out.println("dyehouseId: " + dyehouseId);
        System.out.println("token: " + token);

        ModelMap modelMap = new ModelMap();
        if (!userService.checkToken(token)){
            modelMap.addAttribute("status", 0);
            modelMap.addAttribute("status_code", "ERROR_TOKEN");
            return modelMap;
        }

        modelMap.addAttribute("status", 1);
        modelMap.addAttribute("status_code", "OK");
        modelMap.addAttribute("result", fabricService.findStatisticFabrics(dyehouseId));
        return modelMap;
    }

    @CrossOrigin
    @RequestMapping(value = "getEmailResetPassword", method = RequestMethod.POST)
    @ResponseBody
    public ModelMap getEmailResetPassword(@RequestBody EmailResetPasswordForm emailResetPasswordForm) throws InternalException {
        System.out.println("emailResetPasswordForm: " + emailResetPasswordForm.toString());

        ModelMap modelMap = new ModelMap();
        if (!userService.isEmailExisted(emailResetPasswordForm.getEmail())){
            modelMap.addAttribute("status", 0);
            modelMap.addAttribute("status_code", "EMAIL_NOT_EXISTED");
            return modelMap;
        }

        modelMap.addAttribute("status", 1);
        modelMap.addAttribute("status_code", "OK");
        userService.sendEmailResetPassword(emailResetPasswordForm.getEmail());
        return modelMap;
    }

    @CrossOrigin
    @RequestMapping(value = "resetPassword", method = RequestMethod.PUT)
    @ResponseBody
    public ModelMap resetPassword(@RequestBody ResetPasswordForm resetPasswordForm) throws InternalException {
        System.out.println("resetPasswordForm: " + resetPasswordForm.toString());

        ModelMap modelMap = new ModelMap();
        if (!userService.checkToken(resetPasswordForm.getToken())){
            modelMap.addAttribute("status", 0);
            modelMap.addAttribute("status_code", "ERROR_TOKEN_RESET_PASSWORD");
            return modelMap;
        }

        if (!userService.checkExpiredToken(resetPasswordForm.getToken())){
            modelMap.addAttribute("status", 0);
            modelMap.addAttribute("status_code", "ERROR_TOKEN_RESET_PASSWORD");
            return modelMap;
        }

        modelMap.addAttribute("status", 1);
        modelMap.addAttribute("status_code", "OK");
        userService.resetPassword(resetPasswordForm);
        return modelMap;
    }

    @CrossOrigin
    @RequestMapping(value = "checkTokenResetPassword", method = RequestMethod.POST)
    @ResponseBody
    public ModelMap checkTokenResetPassword(@RequestBody TokenResetPasswordForm tokenResetPasswordForm) throws InternalException {
        System.out.println("tokenResetPasswordForm: " + tokenResetPasswordForm.toString());

        ModelMap modelMap = new ModelMap();
        if (!userService.checkToken(tokenResetPasswordForm.getToken())){
            modelMap.addAttribute("status", 0);
            modelMap.addAttribute("status_code", "INVALID");
            return modelMap;
        }

        if (!userService.checkExpiredToken(tokenResetPasswordForm.getToken())){
            modelMap.addAttribute("status", 0);
            modelMap.addAttribute("status_code", "INVALID");
            return modelMap;
        }

        modelMap.addAttribute("status", 1);
        modelMap.addAttribute("status_code", "VALID");
        return modelMap;
    }

    @CrossOrigin
    @RequestMapping(value = "listUser", method = RequestMethod.GET)
    @ResponseBody
    public ModelMap getListUser(@RequestParam("pageIndex") Long pageIndex,
                                @RequestParam("pageSize") Long pageSize,
                                @RequestHeader("token") String token) throws InternalException {
        System.out.println("pageIndex: " + pageIndex);
        System.out.println("pageSize: " + pageSize);
        System.out.println("token: " + token);

        ModelMap modelMap = new ModelMap();
        if (!userService.checkToken(token)){
            modelMap.addAttribute("status", 0);
            modelMap.addAttribute("status_code", "ERROR_TOKEN");
            return modelMap;
        }
        if (!userService.checkTokenAdmin(token)){
            modelMap.addAttribute("status", 0);
            modelMap.addAttribute("status_code", "PERMISSION_DENIED");
            return modelMap;
        }

        modelMap.addAttribute("status", 1);
        modelMap.addAttribute("status_code", "OK");
        modelMap.addAttribute("result", userService.findUserDTOsWithPaging(pageIndex, pageSize));
        return modelMap;
    }

    @CrossOrigin
    @RequestMapping(value = "createDyehouse", method = RequestMethod.POST)
    @ResponseBody
    public ModelMap createDyehouse(@RequestBody CreateDyehouseForm createDyehouseForm,
                                   @RequestHeader("token") String token) throws InternalException {
        System.out.println("createDyehouseForm: " + createDyehouseForm.toString());
        System.out.println("token: " + token);

        ModelMap modelMap = new ModelMap();
        if (!userService.checkToken(token)){
            modelMap.addAttribute("status", 0);
            modelMap.addAttribute("status_code", "ERROR_TOKEN");
            return modelMap;
        }
        if (!userService.checkTokenAdmin(token)){
            modelMap.addAttribute("status", 0);
            modelMap.addAttribute("status_code", "PERMISSION_DENIED");
            return modelMap;
        }

        modelMap.addAttribute("status", 1);
        modelMap.addAttribute("status_code", "OK");
        modelMap.addAttribute("result", dyehouseService.createDyehouse(createDyehouseForm));
        return modelMap;
    }

    @CrossOrigin
    @RequestMapping(value = "getInforExportedFabric", method = RequestMethod.GET)
    @ResponseBody
    public ModelMap getInforExportedFabric(@RequestHeader("token") String token) throws InternalException {
        System.out.println("token: " + token);

        ModelMap modelMap = new ModelMap();
        if (!userService.checkToken(token)){
            modelMap.addAttribute("status", 0);
            modelMap.addAttribute("status_code", "ERROR_TOKEN");
            return modelMap;
        }

        modelMap.addAttribute("status", 1);
        modelMap.addAttribute("status_code", "OK");
        modelMap.addAttribute("result", fabricService.getInforExportedFabric());
        return modelMap;
    }

    @CrossOrigin
    @RequestMapping(value = "getInforCompletedFabricByType", method = RequestMethod.GET)
    @ResponseBody
    public ModelMap getInforCompletedFabricByType(@RequestParam("fabricType") String fabricType,
                                                  @RequestParam("startDate") Double startDate,
                                                  @RequestParam("endDate") Double endDate,
                                                  @RequestHeader("token") String token) throws InternalException {
        System.out.println("fabricType: " + fabricType);
        System.out.println("startDate: " + startDate);
        System.out.println("endDate: " + endDate);
        System.out.println("token: " + token);

        ModelMap modelMap = new ModelMap();
        if (!userService.checkToken(token)){
            modelMap.addAttribute("status", 0);
            modelMap.addAttribute("status_code", "ERROR_TOKEN");
            return modelMap;
        }

        modelMap.addAttribute("status", 1);
        modelMap.addAttribute("status_code", "OK");
        modelMap.addAttribute("result", fabricService.getInforCompletedFabricByType(fabricType, startDate, endDate));
        return modelMap;
    }

    @CrossOrigin
    @RequestMapping(value = "getInforCompletedFabricByDyehouse", method = RequestMethod.GET)
    @ResponseBody
    public ModelMap getInforCompletedFabricByDyehouse(@RequestParam("dyehouseId") Long dyehouseId,
                                                      @RequestParam("startDate") Double startDate,
                                                      @RequestParam("endDate") Double endDate,
                                                      @RequestHeader("token") String token) throws InternalException {
        System.out.println("dyehouseId: " + dyehouseId);
        System.out.println("startDate: " + startDate);
        System.out.println("endDate: " + endDate);
        System.out.println("token: " + token);

        ModelMap modelMap = new ModelMap();
        if (!userService.checkToken(token)){
            modelMap.addAttribute("status", 0);
            modelMap.addAttribute("status_code", "ERROR_TOKEN");
            return modelMap;
        }

        modelMap.addAttribute("status", 1);
        modelMap.addAttribute("status_code", "OK");
        modelMap.addAttribute("result", fabricService.getInforCompletedFabricByDyehouse(dyehouseId, startDate, endDate));
        return modelMap;
    }

    @CrossOrigin
    @RequestMapping(value = "getInforCompletedFabricByDyehouseRecentYear", method = RequestMethod.GET)
    @ResponseBody
    public ModelMap getInforCompletedFabricByDyehouseRecentYear(@RequestParam("dyehouseId") Long dyehouseId,
                                                                @RequestHeader("token") String token) throws InternalException {
        System.out.println("dyehouseId: " + dyehouseId);
        System.out.println("token: " + token);

        ModelMap modelMap = new ModelMap();
        if (!userService.checkToken(token)){
            modelMap.addAttribute("status", 0);
            modelMap.addAttribute("status_code", "ERROR_TOKEN");
            return modelMap;
        }

        modelMap.addAttribute("status", 1);
        modelMap.addAttribute("status_code", "OK");
        modelMap.addAttribute("result", fabricService.getInforCompletedFabricByDyehouseRecentYear(dyehouseId));
        return modelMap;
    }

    @CrossOrigin
    @RequestMapping(value = "completeOrder", method = RequestMethod.POST)
    @ResponseBody
    public ModelMap completeOrder(@RequestBody CompletedIdForm completedIdForm,
                                      @RequestHeader("token") String token) throws InternalException {
        System.out.println("completedIdForm: " + completedIdForm.toString());
        System.out.println("token: " + token);

        ModelMap modelMap = new ModelMap();
        if (!userService.checkToken(token)){
            modelMap.addAttribute("status", 0);
            modelMap.addAttribute("status_code", "ERROR_TOKEN");
            return modelMap;
        }

        modelMap.addAttribute("status", 1);
        modelMap.addAttribute("status_code", "OK");
        modelMap.addAttribute("result", orderService.completeOrder(Long.valueOf(completedIdForm.getOrderId())));
        return modelMap;
    }

    @CrossOrigin
    @RequestMapping(value = "cancelOrder", method = RequestMethod.POST)
    @ResponseBody
    public ModelMap cancelOrder(@RequestBody CancelIdForm cancelIdForm,
                                @RequestHeader("token") String token) throws InternalException {
        System.out.println("cancelIdForm: " + cancelIdForm.toString());
        System.out.println("token: " + token);

        ModelMap modelMap = new ModelMap();
        if (!userService.checkToken(token)){
            modelMap.addAttribute("status", 0);
            modelMap.addAttribute("status_code", "ERROR_TOKEN");
            return modelMap;
        }

        modelMap.addAttribute("status", 1);
        modelMap.addAttribute("status_code", "OK");
        modelMap.addAttribute("result", orderService.cancelOrder(Long.valueOf(cancelIdForm.getOrderId())));
        return modelMap;
    }

    @CrossOrigin
    @RequestMapping(value = "debt", method = RequestMethod.GET)
    @ResponseBody
    public ModelMap getDebt(@RequestParam("dyehouseId") Long dyehouseId,
                            @RequestParam("startDate") Long startDate,
                            @RequestParam("endDate") Long endDate,
                            @RequestHeader("token") String token) throws InternalException {
        System.out.println("dyehouseId: " + dyehouseId);
        System.out.println("startDate: " + new Timestamp(startDate));
        System.out.println("endDate: " + new Timestamp(endDate));
        System.out.println("token: " + token);

        ModelMap modelMap = new ModelMap();
        if (!userService.checkToken(token)){
            modelMap.addAttribute("status", 0);
            modelMap.addAttribute("status_code", "ERROR_TOKEN");
            return modelMap;
        }

        modelMap.addAttribute("status", 1);
        modelMap.addAttribute("status_code", "OK");
        modelMap.addAttribute("result", debtService.getDebt(dyehouseId, new Timestamp(startDate), new Timestamp(endDate)));
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