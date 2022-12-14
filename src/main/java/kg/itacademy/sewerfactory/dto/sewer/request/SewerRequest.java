package kg.itacademy.sewerfactory.dto.sewer.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SewerRequest{

    String fio;

    Long needAmount;

    Long userId;

    Long orderId;

    String phoneNumber;

    Long departmentId;
}
