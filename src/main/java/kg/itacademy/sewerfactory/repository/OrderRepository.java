package kg.itacademy.sewerfactory.repository;

import kg.itacademy.sewerfactory.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query(nativeQuery = true, value = "select orders.* from orders where orders.customer_id = :id")
    List<Order> findAllOrdersByCustomerId(Long id);

    Long countByNewOrderIsTrue();
}

