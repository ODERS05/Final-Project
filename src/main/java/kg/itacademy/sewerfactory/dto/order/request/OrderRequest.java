package kg.itacademy.sewerfactory.dto.order.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderRequest {
    Long customerId;

    String clothType;

    Long amount;

    Integer unitPrice;

    String status;

    Boolean newOrder;
}
