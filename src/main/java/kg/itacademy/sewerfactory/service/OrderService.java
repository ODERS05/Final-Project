package kg.itacademy.sewerfactory.service;

import kg.itacademy.sewerfactory.dto.order.request.OrderRequest;
import kg.itacademy.sewerfactory.dto.order.request.OrderUpdateRequest;
import kg.itacademy.sewerfactory.dto.order.response.OrderResponse;

public interface OrderService extends BaseService<OrderResponse, OrderRequest> {
    Boolean updateOrder(OrderUpdateRequest t);
}
