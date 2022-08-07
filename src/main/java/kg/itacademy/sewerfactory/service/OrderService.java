package kg.itacademy.sewerfactory.service;

import kg.itacademy.sewerfactory.dto.order.request.OrderRequest;
import kg.itacademy.sewerfactory.dto.order.request.OrderUpdateRequest;
import kg.itacademy.sewerfactory.dto.order.response.OrderResponse;
import kg.itacademy.sewerfactory.entity.Order;

import java.util.List;

public interface OrderService extends BaseService<OrderResponse, OrderRequest> {
    Boolean updateOrder(OrderUpdateRequest t);

    List<OrderResponse> getAllOrdersByCustomerId(Long id);

    Long returnNewOrdersCount();

    Boolean deleteAllOrdersByCustomerId(Long id);
}
