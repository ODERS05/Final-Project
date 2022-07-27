package kg.itacademy.sewerfactory.controller;

import kg.itacademy.sewerfactory.dto.order.request.OrderRequest;
import kg.itacademy.sewerfactory.dto.order.response.OrderResponse;
import kg.itacademy.sewerfactory.service.OrderService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@CrossOrigin(origins = "*", maxAge = 8600)
public class OrderController {
    final OrderService orderService;
    @PostMapping("/add-order")
    public OrderResponse addOrder(@RequestBody OrderRequest request) {
        return orderService.save(request);
    }

    @GetMapping
    public List<OrderResponse> getAll(){
        return orderService.getAll();
    }

    @GetMapping("/{id}")
    public OrderResponse findById(@PathVariable Long id) {
        return orderService.findById(id);
    }
}
