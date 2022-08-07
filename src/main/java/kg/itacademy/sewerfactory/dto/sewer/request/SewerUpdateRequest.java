package kg.itacademy.sewerfactory.dto.sewer.request;

import kg.itacademy.sewerfactory.enums.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SewerUpdateRequest {
    Long id;

    String fio;

    Long needAmount;

    String phoneNumber;

    Long orderId;

    Status status;

    String departmentName;
}
