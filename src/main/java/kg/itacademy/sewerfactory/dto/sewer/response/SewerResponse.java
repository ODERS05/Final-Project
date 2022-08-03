package kg.itacademy.sewerfactory.dto.sewer.response;
import kg.itacademy.sewerfactory.enums.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SewerResponse {
    Long id;

    String fio;

    Long needAmount;

    String phoneNumber;

    String clothesType;

    Integer unitPrice;

    Status status;

    String departmentName;

    String email;
}

