package kg.itacademy.sewerfactory.controller;

import kg.itacademy.sewerfactory.dto.customer.request.CustomerRequest;
import kg.itacademy.sewerfactory.dto.customer.request.CustomerUpdateRequest;
import kg.itacademy.sewerfactory.dto.customer.response.CustomerResponse;
import kg.itacademy.sewerfactory.service.CustomerService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@CrossOrigin(origins = "*", maxAge = 8600)
public class CustomerController {
    final CustomerService customerService;

    @PostMapping("/add-customer")
    public CustomerResponse save(@RequestBody CustomerRequest request){
        return customerService.save(request);
    }

    @GetMapping
    public List<CustomerResponse> getAll(){
        return customerService.getAll();
    }

    @GetMapping("/{id}")
    public CustomerResponse findById(@PathVariable Long id){
        return customerService.findById(id);
    }

    @DeleteMapping("/{id}")
    public CustomerResponse delete(@PathVariable Long id){
        return customerService.delete(id);
    }

    @PutMapping("/{id}")
    public Boolean updateCustomer(@RequestBody CustomerUpdateRequest request, @PathVariable Long id){
        request.setId(id);
        return customerService.updateCustomer(request);
    }
}
