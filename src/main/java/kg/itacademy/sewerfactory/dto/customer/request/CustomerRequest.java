package kg.itacademy.sewerfactory.dto.customer.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerRequest {
    Long userId;

    String fio;

    String phoneNumber;

    List<Long> orderId;
}
