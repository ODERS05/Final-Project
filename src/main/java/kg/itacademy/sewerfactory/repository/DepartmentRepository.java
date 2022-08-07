package kg.itacademy.sewerfactory.repository;

import kg.itacademy.sewerfactory.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    @Query(nativeQuery = true, value = "select d.* from department d where d.department_name = :findByDepartmentName")
    Department findByDepartmentName(String findByDepartmentName);

}
