package kg.itacademy.sewerfactory.dto.customer.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerUpdateRequest {
    Long id;

    String fio;

    String phoneNumber;
}
