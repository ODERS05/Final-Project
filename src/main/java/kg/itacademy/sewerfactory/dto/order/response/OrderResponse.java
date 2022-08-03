package kg.itacademy.sewerfactory.dto.order.response;

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

    String status;

    Boolean newOrder;

    BigDecimal totalCost;

    String fio;
}
