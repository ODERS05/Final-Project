package kg.itacademy.sewerfactory.repository;

import kg.itacademy.sewerfactory.entity.Role;
import kg.itacademy.sewerfactory.enums.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoles(Roles roles);
}