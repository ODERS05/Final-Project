package kg.itacademy.sewerfactory.dto.department.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DepartmentRequest {
    List<Long> sewerId;
    Long orderId;
    String departmentName;
}
