package kg.itacademy.sewerfactory.dto.sewer.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SewerUpdateRequest {
    Long id;

    String status;

    Long needAmount;
}
