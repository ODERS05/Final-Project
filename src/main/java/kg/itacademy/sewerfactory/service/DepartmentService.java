package kg.itacademy.sewerfactory.service;

import kg.itacademy.sewerfactory.dto.department.request.DepartmentRequest;
import kg.itacademy.sewerfactory.dto.department.request.DepartmentUpdateRequest;
import kg.itacademy.sewerfactory.dto.department.response.DepartmentResponse;

public interface DepartmentService extends BaseService<DepartmentResponse, DepartmentRequest>{
    Boolean updateDepartment(DepartmentUpdateRequest t);
}
