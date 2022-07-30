package kg.itacademy.sewerfactory.service.impl;

import kg.itacademy.sewerfactory.dto.customer.request.CustomerRequest;
import kg.itacademy.sewerfactory.dto.customer.response.CustomerResponse;
import kg.itacademy.sewerfactory.dto.order.response.OrderResponse;
import kg.itacademy.sewerfactory.dto.sewer.response.SewerResponse;
import kg.itacademy.sewerfactory.entity.Customer;
import kg.itacademy.sewerfactory.entity.Order;
import kg.itacademy.sewerfactory.exception.NotUniqueCustomer;
import kg.itacademy.sewerfactory.mapper.CustomerMapper;
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
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerServiceImpl implements CustomerService {
    final CustomerRepository customerRepository;
    @Override
    public CustomerResponse save(CustomerRequest t) {
        try {
            Customer customer = customerRepository.save(Customer.builder()
                    .id(t.getUserId())
                    .fio(t.getFio())
                    .phoneNumber(t.getPhoneNumber())
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
        List<CustomerResponse> customerResponses = customerRepository.findAll().stream()
                .map(customer -> CustomerResponse.builder()
                        .id(customer.getId())
                        .fio(customer.getFio())
                        .email(customer.getUser().getEmail())
                        .phoneNumber(customer.getPhoneNumber())
                        .build()).collect(Collectors.toList());
        return customerResponses;

    }

    @Override
    public CustomerResponse findById(Long id) {
        return CustomerMapper.INSTANCE.toCustomerResponse(customerRepository.getById(id));
    }
}
