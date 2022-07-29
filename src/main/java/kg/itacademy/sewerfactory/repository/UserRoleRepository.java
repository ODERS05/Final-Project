package kg.itacademy.sewerfactory.repository;

import kg.itacademy.sewerfactory.entity.User;
import kg.itacademy.sewerfactory.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    UserRole findByUser(User user);
}
