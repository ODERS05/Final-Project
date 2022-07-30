package kg.itacademy.sewerfactory.service.impl;

import kg.itacademy.sewerfactory.dto.order.request.OrderRequest;
import kg.itacademy.sewerfactory.dto.order.request.OrderUpdateRequest;
import kg.itacademy.sewerfactory.dto.order.response.OrderResponse;
import kg.itacademy.sewerfactory.entity.Customer;
import kg.itacademy.sewerfactory.entity.Order;
import kg.itacademy.sewerfactory.exception.CustomerNotFoundException;
import kg.itacademy.sewerfactory.mapper.OrderMapper;
import kg.itacademy.sewerfactory.repository.CustomerRepository;
import kg.itacademy.sewerfactory.repository.OrderRepository;
import kg.itacademy.sewerfactory.service.OrderService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    final OrderRepository orderRepository;
    final CustomerRepository customerRepository;
    @Override
    public OrderResponse save(OrderRequest t) {
        Customer customer = customerRepository.findById(t.getCustomerId()).orElseThrow(() -> new CustomerNotFoundException("Такого заказчика нет", HttpStatus.BAD_REQUEST));
        Order order = orderRepository.save(Order.builder()
                .clothesType(t.getClothesType())
                .amount(t.getAmount())
                .unitPrice(t.getUnitPrice())
                .status(t.getStatus())
                .newOrder(true)
                .customer(customer)
                .build());

        return OrderMapper.INSTANCE.toOrderResponse(order);
    }

    @Override
    public List<OrderResponse> getAll() {
        return OrderMapper.INSTANCE.toOrdersResponse(orderRepository.findAll());
    }

    @Override
    public OrderResponse findById(Long id) {
        return OrderMapper.INSTANCE.toOrderResponse(orderRepository.getById(id));
    }

    @Override
    public Boolean updateOrder(OrderUpdateRequest t) {
        Order order = orderRepository.getById(t.getId());
        order.setNewOrder(t.getNewOrder());
        order.setUnitPrice(t.getUnitPrice());
        order.setAmount(t.getAmount());
        order.setClothesType(t.getClothesType());
        order.setStatus(t.getStatus());
        orderRepository.save(order);
        return order.getId() != null;
    }

    @Override
    public List<OrderResponse> getAllOrdersByCustomerId(Long id) {
        List<Order> orders = orderRepository.findAll();
        List<OrderResponse> orderResponses = new ArrayList<>();
        for (Order order: orders) {
            orderResponses.add(orderRepository.findAllOrdersByCustomerId(id));
        }
        return orderResponses;
    }

}
