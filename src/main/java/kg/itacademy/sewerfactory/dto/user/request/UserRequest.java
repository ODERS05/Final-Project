package kg.itacademy.sewerfactory.dto.user.request;

import kg.itacademy.sewerfactory.enums.Roles;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequest {
    String login;

    String password;

    String email;

    Roles role;
}
