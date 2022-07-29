package kg.itacademy.sewerfactory.dto.user.response;

import kg.itacademy.sewerfactory.enums.Roles;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    Long id;

    String login;

    String email;

    Roles role;
}