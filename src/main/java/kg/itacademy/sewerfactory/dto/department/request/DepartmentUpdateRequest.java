package kg.itacademy.sewerfactory.dto.department.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DepartmentUpdateRequest {
    String departmentName;

    Long orderId;
}
