package kg.itacademy.sewerfactory.service.impl;


import kg.itacademy.sewerfactory.dto.user.request.UserAuthRequest;
import kg.itacademy.sewerfactory.dto.user.request.UserRequest;
import kg.itacademy.sewerfactory.dto.user.request.UserUpdateRequest;
import kg.itacademy.sewerfactory.dto.user.response.UserResponse;
import kg.itacademy.sewerfactory.entity.Role;
import kg.itacademy.sewerfactory.entity.User;
import kg.itacademy.sewerfactory.entity.UserRole;
import kg.itacademy.sewerfactory.enums.Roles;
import kg.itacademy.sewerfactory.exception.NotUniqueRecord;
import kg.itacademy.sewerfactory.exception.UserSignInException;
import kg.itacademy.sewerfactory.mapper.UserMapper;
import kg.itacademy.sewerfactory.model.AuthorizationModel;
import kg.itacademy.sewerfactory.repository.RoleRepository;
import kg.itacademy.sewerfactory.repository.UserRepository;
import kg.itacademy.sewerfactory.repository.UserRoleRepository;
import kg.itacademy.sewerfactory.service.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    final UserRepository userRepository;

    final PasswordEncoder passwordEncoder;

    final RoleRepository roleRepository;

    final UserRoleRepository userRoleRepository;

    @Override
    public UserResponse save(UserRequest t) {
        try {
            User user = userRepository
                    .save(User.builder()
                            .login(t.getLogin())
                            .email(t.getEmail())
                            .password(passwordEncoder.encode(t.getPassword()))
                            .isActive(true)
                            .build());
            UserRole userRole = new UserRole();
            userRole.setUser(userRepository.save(user));
            if (t.getRoles().equals(Roles.ROLE_CUSTOMER.name())) {
                userRole.setRole(roleRepository.findById(1L).get());
            } else {
                userRole.setRole(roleRepository.findById(2L).get());
            }
            userRoleRepository.save(userRole);
            return UserResponse.builder()
                    .roles(t.getRoles())
                    .email(user.getEmail())
                    .id(user.getId())
                    .login(user.getLogin()).build();
        } catch (Exception ignored) {
            throw new NotUniqueRecord("Не уникальный логин", HttpStatus.BAD_REQUEST);
        }

    }

    @Override
    public AuthorizationModel getToken(UserAuthRequest request) throws UserSignInException {
        User user = userRepository.findByLoginOrEmail(request.getEmail());
        boolean isMatches = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (isMatches) {
            return AuthorizationModel.builder().token("Basic " + new String(Base64.getEncoder()
                            .encode((user.getLogin() + ":" + request.getPassword()).getBytes())))
                    .user(User.builder()
                            .login(user.getLogin())
                            .password(user.getPassword())
                            .email(user.getEmail())
                            .isActive(user.getIsActive()).build())
                    .build();
        } else {
            throw new UserSignInException("Неправильный логин или пароль!", HttpStatus.NOT_FOUND);
        }
    }


    @Override
    public Boolean updateUser(UserUpdateRequest t) {
        User user = userRepository.getById(t.getId());
        user.setEmail(t.getEmail());
        user.setPassword(passwordEncoder.encode(t.getPassword()));
        user.setLogin(t.getLogin());
        userRepository.save(user);
        return user.getId() != null;
    }

    @Override
    public List<UserResponse> getAll() {
        return UserMapper.INSTANCE.toUsersResponse(userRepository.findAll());
    }

    @Override
    public UserResponse findById(Long id) {
        return UserMapper.INSTANCE.toUserResponse(userRepository.getById(id));
    }
}

