package kg.itacademy.sewerfactory.dto.order.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderUpdateRequest {
    Long id;

    String clothesType;

    Long amount;

    Integer unitPrice;

    String status;

    Boolean newOrder;
}
