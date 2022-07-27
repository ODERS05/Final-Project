package kg.itacademy.sewerfactory.service;

import kg.itacademy.sewerfactory.dto.sewer.request.SewerRequest;
import kg.itacademy.sewerfactory.dto.sewer.request.SewerUpdateRequest;
import kg.itacademy.sewerfactory.dto.sewer.response.SewerResponse;

import java.math.BigDecimal;

public interface SewerService extends BaseService<SewerResponse, SewerRequest> {
    Boolean updateSewer(SewerUpdateRequest t);
    BigDecimal countSewerSalary(Long id);
}