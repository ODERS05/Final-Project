package kg.itacademy.sewerfactory.service.impl;

import kg.itacademy.sewerfactory.dto.order.request.OrderRequest;
import kg.itacademy.sewerfactory.dto.order.request.OrderUpdateRequest;
import kg.itacademy.sewerfactory.dto.order.response.OrderResponse;
import kg.itacademy.sewerfactory.dto.order.response.OrderResponseInterface;
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
import java.util.List;
import java.util.stream.Collectors;


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
                .status("Waiting")
                .newOrder(true)
                .customer(customer)
                .build());

        return OrderResponse.builder()
                .id(order.getId())
                .newOrder(order.getNewOrder())
                .status(order.getStatus())
                .unitPrice(order.getUnitPrice())
                .amount(order.getAmount())
                .clothesType(order.getClothesType())
                .fio(order.getCustomer().getFio())
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
                .build()).collect(Collectors.toList());
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
    public List<OrderResponseInterface> getAllOrdersByCustomerId(Long id) {
        List<OrderResponseInterface> orderResponses = orderRepository.findAllOrdersByCustomerId(id);
        return orderResponses;
    }

}
