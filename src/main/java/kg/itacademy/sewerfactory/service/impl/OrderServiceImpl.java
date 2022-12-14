package kg.itacademy.sewerfactory.service.impl;

import kg.itacademy.sewerfactory.dto.department.request.DepartmentUpdateRequest;
import kg.itacademy.sewerfactory.dto.order.request.OrderRequest;
import kg.itacademy.sewerfactory.dto.order.request.OrderUpdateRequest;
import kg.itacademy.sewerfactory.dto.order.response.OrderResponse;
import kg.itacademy.sewerfactory.entity.Customer;
import kg.itacademy.sewerfactory.entity.Department;
import kg.itacademy.sewerfactory.entity.Order;
import kg.itacademy.sewerfactory.enums.Status;
import kg.itacademy.sewerfactory.exception.CustomerNotFoundException;
import kg.itacademy.sewerfactory.repository.CustomerRepository;
import kg.itacademy.sewerfactory.repository.DepartmentRepository;
import kg.itacademy.sewerfactory.repository.OrderRepository;
import kg.itacademy.sewerfactory.service.DepartmentService;
import kg.itacademy.sewerfactory.service.OrderService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;


@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    final OrderRepository orderRepository;
    final CustomerRepository customerRepository;
    final DepartmentService departmentService;
    final DepartmentRepository departmentRepository;

    @Override
    public OrderResponse save(OrderRequest t) {
        Customer customer = customerRepository.findById(t.getCustomerId()).orElseThrow(() -> new CustomerNotFoundException("Такого заказчика нет", HttpStatus.BAD_REQUEST));
        BigDecimal totalCost = BigDecimal.valueOf(t.getAmount() * t.getUnitPrice());
        Order order = orderRepository.save(Order.builder()
                .clothesType(t.getClothesType())
                .amount(t.getAmount())
                .unitPrice(t.getUnitPrice())
                .description(t.getDescription())
                .status(Status.WAITING)
                .newOrder(true)
                .customer(customer)
                .totalCost(totalCost)
                .build());

        return OrderResponse.builder()
                .id(order.getId())
                .newOrder(order.getNewOrder())
                .status(order.getStatus())
                .unitPrice(order.getUnitPrice())
                .amount(order.getAmount())
                .clothesType(order.getClothesType())
                .fio(order.getCustomer().getFio())
                .totalCost(order.getTotalCost())
                .build();
    }

    @Override
    public List<OrderResponse> getAll() {
        return orderRepository.findAll().stream()
                .map(order -> OrderResponse.builder()
                        .newOrder(order.getNewOrder())
                        .id(order.getId())
                        .fio(order.getCustomer().getFio())
                        .clothesType(order.getClothesType())
                        .unitPrice(order.getUnitPrice())
                        .status(order.getStatus())
                        .amount(order.getAmount())
                        .totalCost(order.getTotalCost())
                        .customerId(order.getCustomer().getId())
                        .description(order.getDescription())
                .build()).collect(Collectors.toList());
    }

    @Override
    public OrderResponse findById(Long id) {
        Order order = orderRepository.getById(id);
        return OrderResponse.builder()
                .totalCost(order.getTotalCost())
                .fio(order.getCustomer().getFio())
                .clothesType(order.getClothesType())
                .unitPrice(order.getUnitPrice())
                .status(order.getStatus())
                .newOrder(order.getNewOrder())
                .id(order.getId())
                .amount(order.getAmount())
                .description(order.getDescription())
                .customerId(order.getCustomer().getId())
                .build();
    }

    @Override
    public OrderResponse delete(Long id) {
        Order order = orderRepository.getById(id);
        Department department = departmentRepository.findDepartmentByOrderId(id);
        if (department != null){
            departmentService.updateDepartment(DepartmentUpdateRequest.builder()
                    .id(department.getId())
                    .build());
        }
        orderRepository.delete(order);
        return OrderResponse.builder().build();
    }

    @Override
    public Boolean updateOrder(OrderUpdateRequest t) {
        Order order = orderRepository.getById(t.getId());
        if (t.getNewOrder() != null){
            order.setNewOrder(t.getNewOrder());
        }
        if (t.getUnitPrice() != null){
            order.setUnitPrice(t.getUnitPrice());
        }
        if (t.getAmount() != null){
            order.setAmount(t.getAmount());
        }
        if (t.getClothesType()!= null){
            order.setClothesType(t.getClothesType());
        }
        if (t.getStatus() != null){
            order.setStatus(t.getStatus());
        }
        if (t.getDescription() != null){
            order.setDescription(t.getDescription());
        }
        orderRepository.save(order);
        return order.getId() != null;
    }

    @Override
    public List<OrderResponse> getAllOrdersByCustomerId(Long id) {
        return orderRepository.findAllOrdersByCustomerId(id).stream()
                .map(order -> OrderResponse.builder()
                        .amount(order.getAmount())
                        .id(order.getId())
                        .newOrder(order.getNewOrder())
                        .status(order.getStatus())
                        .unitPrice(order.getUnitPrice())
                        .fio(order.getCustomer().getFio())
                        .clothesType(order.getClothesType())
                        .totalCost(order.getTotalCost())
                        .description(order.getDescription())
                        .build()).collect(Collectors.toList());
    }

    @Override
    public Long returnNewOrdersCount(){
        return orderRepository.countByNewOrderIsTrue();
    }

    @Override
    public void deleteAllOrdersByCustomerId(Long id) {
        List<Order> orders = orderRepository.findAllOrdersByCustomerId(id);
        for (Order order : orders) {
            orderRepository.delete(order);
        }
    }

}
