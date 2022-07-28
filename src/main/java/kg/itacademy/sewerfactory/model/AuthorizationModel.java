package kg.itacademy.sewerfactory.model;

import kg.itacademy.sewerfactory.entity.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthorizationModel {
    String token;

    User user;

}
