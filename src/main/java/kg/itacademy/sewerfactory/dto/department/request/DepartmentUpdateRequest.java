package kg.itacademy.sewerfactory.dto.department.request;

import kg.itacademy.sewerfactory.enums.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DepartmentUpdateRequest {
    Long id;

    String departmentName;

    Long orderId;

    Status status;
}
