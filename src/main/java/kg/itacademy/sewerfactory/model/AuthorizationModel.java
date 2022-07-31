package kg.itacademy.sewerfactory.model;

import kg.itacademy.sewerfactory.dto.user.response.UserResponse;
import kg.itacademy.sewerfactory.entity.User;
import kg.itacademy.sewerfactory.entity.UserRole;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthorizationModel {
    String token;

    UserRole userRole;

}
