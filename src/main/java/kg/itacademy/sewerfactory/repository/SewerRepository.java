package kg.itacademy.sewerfactory.repository;

import kg.itacademy.sewerfactory.entity.Sewer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SewerRepository extends JpaRepository<Sewer, Long> {
    @Query(nativeQuery = true, value = "select s.* from sewer s where s.department_id = :id")
    List<Sewer>  findAllSewersByDepartmentId(Long id);
}