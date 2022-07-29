package kg.itacademy.sewerfactory.repository;

import kg.itacademy.sewerfactory.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query(nativeQuery = true, value = "select r.* from role r where r.roles =:roles")
    Role findFirstByRoles(String roles);
}