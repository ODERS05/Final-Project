package kg.itacademy.sewerfactory.mapper;

import kg.itacademy.sewerfactory.dto.customer.request.CustomerRequest;
import kg.itacademy.sewerfactory.dto.customer.response.CustomerResponse;
import kg.itacademy.sewerfactory.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);
    Customer toCustomerEntity(CustomerRequest customerRequest);
    CustomerResponse toCustomerResponse(Customer customer);
    List<CustomerResponse> toCustomersResponse(List<Customer> customers);
}
