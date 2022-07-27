package kg.itacademy.sewerfactory.repository;

import kg.itacademy.sewerfactory.entity.Sewer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SewerRepository extends JpaRepository<Sewer, Long> {
}