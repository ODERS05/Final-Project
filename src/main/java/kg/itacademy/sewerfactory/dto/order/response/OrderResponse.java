package kg.itacademy.sewerfactory.dto.order.response;

import kg.itacademy.sewerfactory.enums.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse {
    Long id;

    String clothesType;

    Long amount;

    Integer unitPrice;

    Status status;

    Boolean newOrder;

    BigDecimal totalCost;

    String description;

    String fio;

    Long customerId;
}
