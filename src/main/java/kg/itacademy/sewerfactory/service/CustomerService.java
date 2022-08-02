package kg.itacademy.sewerfactory.service;

import kg.itacademy.sewerfactory.dto.customer.request.CustomerRequest;
import kg.itacademy.sewerfactory.dto.customer.request.CustomerUpdateRequest;
import kg.itacademy.sewerfactory.dto.customer.response.CustomerResponse;

public interface CustomerService extends BaseService<CustomerResponse, CustomerRequest>{
    Boolean updateCustomer(CustomerUpdateRequest t);
}
