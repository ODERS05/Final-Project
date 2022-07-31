package kg.itacademy.sewerfactory.repository;

import kg.itacademy.sewerfactory.dto.order.response.OrderResponse;
import kg.itacademy.sewerfactory.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query(nativeQuery = true, value = "select orders.* from orders where orders.customer_id = :id")
    OrderResponse findAllOrdersByCustomerId(Long id);
}

