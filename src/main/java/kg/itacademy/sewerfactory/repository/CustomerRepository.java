package kg.itacademy.sewerfactory.repository;

import kg.itacademy.sewerfactory.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{
}
