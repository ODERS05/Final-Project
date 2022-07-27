package kg.itacademy.sewerfactory.repository;

import kg.itacademy.sewerfactory.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
