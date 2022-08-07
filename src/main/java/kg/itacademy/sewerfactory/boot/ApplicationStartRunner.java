package kg.itacademy.sewerfactory.boot;

import kg.itacademy.sewerfactory.entity.Department;
import kg.itacademy.sewerfactory.repository.DepartmentRepository;
import kg.itacademy.sewerfactory.repository.RoleRepository;
import kg.itacademy.sewerfactory.repository.UserRepository;
import kg.itacademy.sewerfactory.repository.UserRoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartRunner implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final DepartmentRepository departmentRepository;

    public ApplicationStartRunner(RoleRepository roleRepository, UserRepository userRepository, UserRoleRepository userRoleRepository, DepartmentRepository departmentRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public void run(String... args) throws Exception {
//       roleRepository.save(
//               Role.builder()
//                       .id(1L)
//                .roles(Roles.ROLE_CUSTOMER).build());
//        roleRepository.save(
//                Role.builder()
//                        .id(2L)
//                        .roles(Roles.ROLE_SEWER).build());
//        roleRepository.save(
//                Role.builder()
//                        .id(3L)
//                        .roles(Roles.ROLE_ADMIN)
//                        .build()
//        );
    }
}

