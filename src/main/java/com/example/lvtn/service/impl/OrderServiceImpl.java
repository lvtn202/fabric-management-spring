package com.example.lvtn.service.impl;

import com.example.lvtn.dao.ColorRepository;
import com.example.lvtn.dao.DyehouseRepository;
import com.example.lvtn.dao.OrderRepository;
import com.example.lvtn.dao.UserRepository;
import com.example.lvtn.dom.ImportSlip;
import com.example.lvtn.dom.Order;
import com.example.lvtn.dto.CreateOrderForm;
import com.example.lvtn.dto.OrderDTO;
import com.example.lvtn.service.OrderService;
import com.example.lvtn.service.UserService;
import com.example.lvtn.utils.EmailSender;
import com.example.lvtn.utils.InternalException;
import com.example.lvtn.utils.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.TimeZone;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private DyehouseRepository dyehouseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ColorRepository colorRepository;

    @Autowired
    public EmailSender emailSender;

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public List<OrderDTO> findOrderDTOsByDyehouseIdWithPaging(Long dyehouseId, Long pageIndex, Long pageSize) throws InternalException {
        try {
            List<Order> listOrder;
            List<OrderDTO> listOrderDTO = new ArrayList<OrderDTO>();
            if(dyehouseId < 0){
                listOrder = orderRepository.findOrders();
            } else {
                listOrder = orderRepository.findOrdersByDyehouseId(dyehouseId);
            }
            for (Order order: listOrder){
                if(order.getStatus().equals(OrderStatus.CREATED)){
                    listOrderDTO.add(OrderDTO.convertOrderToOrderDTO(order));
                }
            }
            for (Order order: listOrder){
                if(order.getStatus().equals(OrderStatus.IN_PROGRESS)){
                    listOrderDTO.add(OrderDTO.convertOrderToOrderDTO(order));
                }
            }
            for (Order order: listOrder){
                if(order.getStatus().equals(OrderStatus.COMPLETED)){
                    listOrderDTO.add(OrderDTO.convertOrderToOrderDTO(order));
                }
            }
            for (Order order: listOrder){
                if(!order.getStatus().equals(OrderStatus.CREATED)
                        && !order.getStatus().equals(OrderStatus.IN_PROGRESS)
                        && !order.getStatus().equals(OrderStatus.COMPLETED)){
                    listOrderDTO.add(OrderDTO.convertOrderToOrderDTO(order));
                }
            }
            int fromIndex = (int)(pageIndex * pageSize) > listOrderDTO.size() ? listOrderDTO.size() : (int)(pageIndex * pageSize);
            int toIndex = (int)(pageIndex * pageSize + pageSize) > listOrderDTO.size() ? listOrderDTO.size() : (int)(pageIndex * pageSize + pageSize);
            return listOrderDTO.subList(fromIndex,toIndex);
        } catch (Exception e){
            e.printStackTrace();
            throw new InternalException(e.getMessage());
        }
    }

    @Override
    public ModelMap findDetailOrderById(Long id) throws InternalException {
        try {
            Order order = orderRepository.findOrderById(id);

            ModelMap modelMap = new ModelMap();
            modelMap.addAttribute("id", order.getId());
            modelMap.addAttribute("dyehouseName", order.getDyehouse().getName());
            modelMap.addAttribute("fabricType", order.getColor().getFabricType().getType());
            modelMap.addAttribute("color", order.getColor().getName());
            modelMap.addAttribute("orderLength", String.format("%.1f", order.getOrderLength()));
            modelMap.addAttribute("status", order.getStatus());
            modelMap.addAttribute("createDate", String.format("%tQ", order.getCreateDate()));
            modelMap.addAttribute("employee", order.getUser().getLastName());
            return modelMap;
        }catch (Exception e){
            e.printStackTrace();
            throw new InternalException(e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public ModelMap createOrder(CreateOrderForm createOrderForm) throws InternalException {
        try {
            Order order = new Order(
                    OrderStatus.CREATED,
                    createOrderForm.getCreateDate(),
                    createOrderForm.getOrderLength(),
                    (double) 0,
                    dyehouseRepository.findDyehouseById(createOrderForm.getDyehouseId()),
                    userRepository.findUsersById(createOrderForm.getUserId()),
                    colorRepository.findColorByFabricTypeAndColor(createOrderForm.getFabricType(), createOrderForm.getColor()),
                    new HashSet<ImportSlip>()
            );
            Order newOrder = orderRepository.save(order);

            String email = newOrder.getDyehouse().getEmail();
            String subject = "Đơn đặt hàng mới";
            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+7"));
            String content = "Xin chào xưởng " + newOrder.getDyehouse().getName() + ",\n\n"
                    + "Đơn đặt hàng mới đã được tạo.\n"
                    + "Mã đơn: " + newOrder.getId().toString() + "\n"
                    + "Loại vải: " + newOrder.getColor().getFabricType().getType() + "\n"
                    + "Màu: " + newOrder.getColor().getName() + "\n"
                    + "Độ dài đặt: " + String.format("%,.1f", newOrder.getOrderLength()) + " (m)\n"
                    + "Ngày đặt: " + dateFormat.format(newOrder.getCreateDate()) + "\n\n"
                    + "Trân trọng !";
            emailSender.sendEmail(email, subject, content);

            ModelMap modelMap = new ModelMap();
            modelMap.addAttribute("id", newOrder.getId());
            modelMap.addAttribute("dyehouseName", newOrder.getDyehouse().getName());
            modelMap.addAttribute("fabricType", newOrder.getColor().getFabricType().getType());
            modelMap.addAttribute("color", newOrder.getColor().getName());
            modelMap.addAttribute("orderLength", String.format("%.1f", newOrder.getOrderLength()));
            modelMap.addAttribute("createDate", String.format("%tQ", newOrder.getCreateDate()));
            modelMap.addAttribute("employee", newOrder.getUser().getLastName());
            return modelMap;
        } catch (Exception e){
            e.printStackTrace();
            throw new InternalException(e.getMessage());
        }
    }

    @Override
    public List<OrderDTO> findOrderDTOsByFabricTypeAndColor(Long dyehouseId, String fabricType, String color) throws InternalException {
        try {
            List<Order> listOrder = orderRepository.findOrdersOfDyehouseByFabricTypeAndColor(dyehouseId, fabricType, color);
            List<OrderDTO> listOrderDTO = new ArrayList<>();
            for (Order order: listOrder){
                if(order.getStatus().equals(OrderStatus.CREATED)){
                    listOrderDTO.add(OrderDTO.convertOrderToOrderDTO(order));
                }
            }
            for (Order order: listOrder){
                if(order.getStatus().equals(OrderStatus.IN_PROGRESS)){
                    listOrderDTO.add(OrderDTO.convertOrderToOrderDTO(order));
                }
            }
            for (Order order: listOrder){
                if(order.getStatus().equals(OrderStatus.COMPLETED)){
                    listOrderDTO.add(OrderDTO.convertOrderToOrderDTO(order));
                }
            }
            for (Order order: listOrder){
                if(!order.getStatus().equals(OrderStatus.CREATED)
                        && !order.getStatus().equals(OrderStatus.IN_PROGRESS)
                        && !order.getStatus().equals(OrderStatus.COMPLETED)){
                    listOrderDTO.add(OrderDTO.convertOrderToOrderDTO(order));
                }
            }
            return listOrderDTO;
        } catch (Exception e){
            e.printStackTrace();
            throw new InternalException(e.getMessage());
        }
    }

    @Override
    public ModelMap completeOrder(Long orderId) throws InternalException {
        try {
            if(orderId < 0L){
                throw new InternalException("ERROR_ID");
            }
            Order order = orderRepository.findOrderById(orderId);
            if (order.getStatus().equals(OrderStatus.COMPLETED)){
                throw new InternalException("ORDER_COMPLETED");
            } else {
                order.setStatus(OrderStatus.COMPLETED);
                orderRepository.save(order);

                String email = order.getDyehouse().getEmail();
                String subject = "Đơn đặt hàng hoàn thành";
                DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
                dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+7"));
                String content = "Xin chào xưởng " + order.getDyehouse().getName() + ",\n\n"
                        + "Đơn đặt hàng đã hoàn thành.\n"
                        + "Mã đơn: " + order.getId().toString() + "\n"
                        + "Loại vải: " + order.getColor().getFabricType().getType() + "\n"
                        + "Màu: " + order.getColor().getName() + "\n"
                        + "Độ dài đặt: " + String.format("%,.1f", order.getOrderLength()) + "\n"
                        + "Ngày đặt: " + dateFormat.format(order.getCreateDate()) + "\n\n"
                        + "Trân trọng !";
                emailSender.sendEmail(email, subject, content);

                ModelMap modelMap = new ModelMap();
                modelMap.addAttribute("id", order.getId());
                modelMap.addAttribute("dyehouseName", order.getDyehouse().getName());
                modelMap.addAttribute("fabricType", order.getColor().getFabricType().getType());
                modelMap.addAttribute("color", order.getColor().getName());
                modelMap.addAttribute("orderLength", String.format("%.1f", order.getOrderLength()));
                modelMap.addAttribute("status", order.getStatus());
                modelMap.addAttribute("createDate", String.format("%tQ", order.getCreateDate()));
                modelMap.addAttribute("employee", order.getUser().getLastName());
                return modelMap;
            }
        } catch (Exception e){
            e.printStackTrace();
            throw new InternalException(e.getMessage());
        }
    }

    @Override
    public ModelMap cancelOrder(Long orderId) throws InternalException {
        try {
            if(orderId < 0L){
                throw new InternalException("ERROR_ID");
            }
            Order order = orderRepository.findOrderById(orderId);
            if (!order.getStatus().equals(OrderStatus.CREATED)){
                throw new InternalException("CANCEL_ERROR");
            } else {
                order.setStatus(OrderStatus.CANCELLED);
                orderRepository.save(order);

                String email = order.getDyehouse().getEmail();
                String subject = "Đơn đặt hàng đã được hủy";
                DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
                dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+7"));
                String content = "Xin chào xưởng " + order.getDyehouse().getName() +",\n\n"
                        + "Đơn đặt hàng đã được hủy.\n"
                        + "Mã đơn: " + order.getId().toString() + "\n"
                        + "Loại vải: " + order.getColor().getFabricType().getType() + "\n"
                        + "Màu: " + order.getColor().getName() + "\n"
                        + "Độ dài đặt: " + String.format("%,.1f", order.getOrderLength()) + "\n"
                        + "Ngày đặt: " + dateFormat.format(order.getCreateDate()) + "\n\n"
                        + "Trân trọng !";
                emailSender.sendEmail(email, subject, content);

                ModelMap modelMap = new ModelMap();
                modelMap.addAttribute("id", order.getId());
                modelMap.addAttribute("dyehouseName", order.getDyehouse().getName());
                modelMap.addAttribute("fabricType", order.getColor().getFabricType().getType());
                modelMap.addAttribute("color", order.getColor().getName());
                modelMap.addAttribute("orderLength", String.format("%.1f", order.getOrderLength()));
                modelMap.addAttribute("status", order.getStatus());
                modelMap.addAttribute("createDate", String.format("%tQ", order.getCreateDate()));
                modelMap.addAttribute("employee", order.getUser().getLastName());
                return modelMap;
            }
        } catch (Exception e){
            e.printStackTrace();
            throw new InternalException(e.getMessage());
        }
    }
}
