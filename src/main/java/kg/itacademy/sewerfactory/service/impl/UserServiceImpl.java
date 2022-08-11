package kg.itacademy.sewerfactory.service.impl;


import kg.itacademy.sewerfactory.dto.user.request.UserAuthRequest;
import kg.itacademy.sewerfactory.dto.user.request.UserRequest;
import kg.itacademy.sewerfactory.dto.user.request.UserUpdateRequest;
import kg.itacademy.sewerfactory.dto.user.response.UserResponse;
import kg.itacademy.sewerfactory.entity.Customer;
import kg.itacademy.sewerfactory.entity.Sewer;
import kg.itacademy.sewerfactory.entity.User;
import kg.itacademy.sewerfactory.entity.UserRole;
import kg.itacademy.sewerfactory.enums.Roles;
import kg.itacademy.sewerfactory.exception.NotUniqueRecord;
import kg.itacademy.sewerfactory.exception.UserSignInException;
import kg.itacademy.sewerfactory.mapper.UserMapper;
import kg.itacademy.sewerfactory.model.AuthModel;
import kg.itacademy.sewerfactory.repository.*;
import kg.itacademy.sewerfactory.service.CustomerService;
import kg.itacademy.sewerfactory.service.SewerService;
import kg.itacademy.sewerfactory.service.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    final UserRepository userRepository;

    final PasswordEncoder passwordEncoder;

    final RoleRepository roleRepository;

    final UserRoleRepository userRoleRepository;

    final CustomerService customerService;

    final SewerService sewerService;

    @Override
    public UserResponse save(UserRequest t) {
        try {

            User user = userRepository.save(User.builder()
                    .email(t.getEmail())
                    .isActive(true)
                    .password(passwordEncoder.encode(t.getPassword()))
                    .login(t.getLogin())
                    .build());
            UserRole userRole = new UserRole();
            userRole.setUser(userRepository.save(user));
            userRole.setId(user.getId());
            if (t.getRole().equals(Roles.ROLE_CUSTOMER)) {
                userRole.setRole(roleRepository.findById(1L).get());
            } else {
                userRole.setRole(roleRepository.findById(2L).get());
            }
            userRoleRepository.save(userRole);
            return UserResponse.builder()
                    .role(t.getRole())
                    .email(user.getEmail())
                    .id(user.getId())
                    .login(user.getLogin())
                    .token(getToken(t.getLogin(),t.getPassword())).build();
        } catch (Exception ignored) {
            throw new NotUniqueRecord("Не уникальный логин", HttpStatus.BAD_REQUEST);
        }

    }

    @Override
    public UserResponse findByToken() {
        User user = userRepository.findByLoginOrEmail(
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName());
        UserRole userRole = userRoleRepository.findByUser(user);
        return UserResponse.builder()
                .id(user.getId())
                .role(userRole.getRole().getRoles())
                .login(user.getLogin())
                .email(user.getEmail()).build();
    }

    @Override
    public AuthModel auth(UserAuthRequest request) throws UserSignInException {
        User user = userRepository.findByLoginOrEmail(request.getEmail());
        UserRole userRole = userRoleRepository.findByUser(user);
        boolean isMatches = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (isMatches) {
            return AuthModel.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .login(user.getLogin())
                    .roles(userRole.getRole().getRoles())
                    .token(getToken(user.getLogin(), request.getPassword()))
                    .build();
        } else {
            throw new UserSignInException("Неправильный логин или пароль!", HttpStatus.NOT_FOUND);
        }
    }

    private String getToken(String login, String password) {
        return "Basic " + new String(Base64.getEncoder()
                .encode((login + ":" + password).getBytes()));
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
        return userRepository.findAll().stream()
                .map(user -> UserResponse.builder().email(user.getEmail())
                        .login(user.getLogin())
                        .id(user.getId())
                        .role(userRoleRepository.findByUser(user).getRole().getRoles()).build())
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse findById(Long id) {
        return UserMapper.INSTANCE.toUserResponse(userRepository.getById(id));
    }

    @Override
    public UserResponse delete(Long id) {
        User user = userRepository.getById(id);
        customerService.delete(user.getId());
        sewerService.delete(user.getId());
        userRoleRepository.deleteByUserId(user.getId());
        userRepository.delete(user);
        return UserResponse.builder().build();
    }
}

