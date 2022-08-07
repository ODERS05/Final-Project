package kg.itacademy.sewerfactory.dto.department.response;

import kg.itacademy.sewerfactory.dto.sewer.response.SewerResponse;
import kg.itacademy.sewerfactory.enums.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DepartmentResponse {
    Long id;

    String clothType;

    Long amount;

    Integer unitPrice;

    Status status;

    String description;

    String departmentName;

    Status departmentStatus;
}

