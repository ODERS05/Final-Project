package kg.itacademy.sewerfactory.service.impl;

import kg.itacademy.sewerfactory.dto.customer.request.CustomerRequest;
import kg.itacademy.sewerfactory.dto.customer.response.CustomerResponse;
import kg.itacademy.sewerfactory.dto.order.response.OrderResponse;
import kg.itacademy.sewerfactory.dto.sewer.response.SewerResponse;
import kg.itacademy.sewerfactory.entity.Customer;
import kg.itacademy.sewerfactory.entity.Order;
import kg.itacademy.sewerfactory.exception.NotUniqueCustomer;
import kg.itacademy.sewerfactory.repository.CustomerRepository;
import kg.itacademy.sewerfactory.repository.OrderRepository;
import kg.itacademy.sewerfactory.service.CustomerService;
import kg.itacademy.sewerfactory.service.OrderService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.aspectj.weaver.ast.Or;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerServiceImpl implements CustomerService {
    final CustomerRepository customerRepository;
    final OrderRepository orderRepository;
    final OrderService orderService;
    ModelMapper modelMapper;
    @Override
    public CustomerResponse save(CustomerRequest t) {
        try {
            Customer customer = customerRepository.save(Customer.builder()
                    .id(t.getUserId())
                    .fio(t.getFio())
                    .phoneNumber(t.getPhoneNumber())
                    .build());
            List<Order> orders = new ArrayList<>();
            for (int i = 0; i < t.getOrderId().size(); i++) {
                orders.add(orderRepository.findById(t.getOrderId().get(i)).get());
            }
            customer.setOrders(orders);
            customerRepository.save(customer);
            Type listType = new TypeToken<List<OrderResponse>>(){}.getType();
            List<OrderResponse> orderResponses = modelMapper.map(orders,listType);
            return CustomerResponse.builder()
                    .id(customer.getId())
                    .fio(customer.getFio())
                    .orders(orderResponses)
                    .phoneNumber(customer.getPhoneNumber())
                    .email(customer.getUser().getEmail())
                    .build();
        }catch (Exception e){
            throw  new NotUniqueCustomer("Такой заказчик уже есть", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<CustomerResponse> getAll() {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerResponse> customerResponses = new ArrayList<>();
        for (Customer customer: customers) {
            customerResponses.add(CustomerResponse.builder()
                    .id(customer.getId())
                    .phoneNumber(customer.getPhoneNumber())
                    .fio(customer.getFio())
                    .orders(orderService.getAll())
                    .email(customer.getUser().getEmail())
                    .build());
        }
        return customerResponses;
    }

    @Override
    public CustomerResponse findById(Long id) {
        Customer customer = customerRepository.getById(id);
        List<Order> orders = orderRepository.findAll();
        List<OrderResponse> orderResponses = new ArrayList<>();
        for (Order order: orders){
            orderResponses.add(OrderResponse.builder()
                    .id(order.getId())
                    .amount(order.getAmount())
                    .unitPrice(order.getUnitPrice())
                    .clothType(order.getClothesType())
                    .status(order.getStatus())
                    .newOrder(order.getNewOrder())
                    .build());
        }
        return CustomerResponse.builder()
                .id(customer.getId())
                .fio(customer.getFio())
                .phoneNumber(customer.getPhoneNumber())
                .orders(orderResponses)
                .email(customer.getUser().getEmail())
                .build();
    }
}
