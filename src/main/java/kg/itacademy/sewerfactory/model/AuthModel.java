package kg.itacademy.sewerfactory.model;

import kg.itacademy.sewerfactory.entity.Role;
import kg.itacademy.sewerfactory.entity.User;
import kg.itacademy.sewerfactory.enums.Roles;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.MappedSuperclass;

@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthModel{
    String token;

    Long id;

    String email;

    String login;

    Roles roles;
}
