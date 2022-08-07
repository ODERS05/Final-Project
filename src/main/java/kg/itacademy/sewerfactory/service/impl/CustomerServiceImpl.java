package kg.itacademy.sewerfactory.service.impl;

import kg.itacademy.sewerfactory.dto.customer.request.CustomerRequest;
import kg.itacademy.sewerfactory.dto.customer.request.CustomerUpdateRequest;
import kg.itacademy.sewerfactory.dto.customer.response.CustomerResponse;
import kg.itacademy.sewerfactory.entity.Customer;
import kg.itacademy.sewerfactory.entity.User;
import kg.itacademy.sewerfactory.exception.NotUniqueCustomer;
import kg.itacademy.sewerfactory.exception.UserNotFoundException;
import kg.itacademy.sewerfactory.mapper.CustomerMapper;
import kg.itacademy.sewerfactory.repository.CustomerRepository;
import kg.itacademy.sewerfactory.repository.OrderRepository;
import kg.itacademy.sewerfactory.repository.UserRepository;
import kg.itacademy.sewerfactory.service.CustomerService;
import kg.itacademy.sewerfactory.service.OrderService;
import kg.itacademy.sewerfactory.service.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerServiceImpl implements CustomerService {
    final CustomerRepository customerRepository;
    final UserRepository userRepository;
    final OrderService orderService;
    final OrderRepository orderRepository;
    @Override
    public CustomerResponse save(CustomerRequest t) {
        try {
            User user = userRepository.findById(t.getUserId()).orElseThrow(() -> new UserNotFoundException("Такого пользователя нет", HttpStatus.BAD_REQUEST));
            Customer customer = customerRepository.save(Customer.builder()
                    .id(t.getUserId())
                    .fio(t.getFio())
                    .phoneNumber(t.getPhoneNumber())
                    .user(user)
                    .build());
            customerRepository.save(customer);
            return CustomerResponse.builder()
                    .id(customer.getId())
                    .fio(customer.getFio())
                    .phoneNumber(customer.getPhoneNumber())
                    .email(customer.getUser().getEmail())
                    .build();
        }catch (Exception e){
            throw  new NotUniqueCustomer("Такой заказчик уже есть", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<CustomerResponse> getAll() {
        return customerRepository.findAll().stream()
                .map(customer -> CustomerResponse.builder()
                        .id(customer.getId())
                        .fio(customer.getFio())
                        .email(customer.getUser().getEmail())
                        .phoneNumber(customer.getPhoneNumber())
                        .build()).collect(Collectors.toList());

    }

    @Override
    public CustomerResponse findById(Long id) {
        Customer customer = customerRepository.getById(id);
        User user = userRepository.getById(id);
        return CustomerResponse.builder()
                .phoneNumber(customer.getPhoneNumber())
                .email(user.getEmail())
                .fio(customer.getFio())
                .id(customer.getId())
                .build();
    }

    @Override
    public Boolean updateCustomer(CustomerUpdateRequest t) {
        Customer customer = customerRepository.getById(t.getId());
        customer.setFio(t.getFio());
        customer.setPhoneNumber(t.getPhoneNumber());
        customerRepository.save(customer);
        return customer.getId() != null;
    }

    @Override
    public CustomerResponse delete(Long id) {
        Customer customer = customerRepository.getById(id);
        orderService.deleteAllOrdersByCustomerId(id);
        customerRepository.delete(customer);
        return CustomerResponse.builder().build();
    }

}
