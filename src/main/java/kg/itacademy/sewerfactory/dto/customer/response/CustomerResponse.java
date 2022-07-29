package kg.itacademy.sewerfactory.dto.customer.response;

import kg.itacademy.sewerfactory.dto.order.response.OrderResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerResponse {
    Long id;

    String email;

    String fio;

    String phoneNumber;
}
