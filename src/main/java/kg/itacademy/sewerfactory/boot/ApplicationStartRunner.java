package kg.itacademy.sewerfactory.boot;

import kg.itacademy.sewerfactory.entity.Role;
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

    public ApplicationStartRunner(RoleRepository roleRepository, UserRepository userRepository, UserRoleRepository userRoleRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
//       roleRepository.save(
//               Role.builder()
//                .nameRole("ROLE_ADMIN").build());
//        roleRepository.save(
//               Role.builder()
//                      .nameRole("ROLE_USER")
//                        .build()
//        );
//        roleRepository.save(
//                Role.builder()
//                        .nameRole("ROLE_SEWER").build());
//        roleRepository.save(
//                Role.builder()
//                        .nameRole("ROLE_CUSTOMER")
//                        .build()
//        );
    }
}

