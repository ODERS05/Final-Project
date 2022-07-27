package kg.itacademy.sewerfactory.dto.user.request;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserAuthRequest {
    String Email;

    String password;
}
