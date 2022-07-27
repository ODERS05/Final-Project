package kg.itacademy.sewerfactory.service.impl;

import kg.itacademy.sewerfactory.dto.order.request.OrderRequest;
import kg.itacademy.sewerfactory.dto.order.response.OrderResponse;
import kg.itacademy.sewerfactory.entity.Order;
import kg.itacademy.sewerfactory.mapper.OrderMapper;
import kg.itacademy.sewerfactory.repository.OrderRepository;
import kg.itacademy.sewerfactory.service.OrderService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    final OrderRepository orderRepository;
    @Override
    public OrderResponse save(OrderRequest t) {
        Order order = orderRepository.save(Order.builder()
                .clothesType(t.getClothType())
                .amount(t.getAmount())
                .unitPrice(t.getUnitPrice())
                .status(t.getStatus())
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
}
