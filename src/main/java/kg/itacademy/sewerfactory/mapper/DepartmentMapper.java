package kg.itacademy.sewerfactory.mapper;

import kg.itacademy.sewerfactory.dto.department.request.DepartmentRequest;
import kg.itacademy.sewerfactory.dto.department.response.DepartmentResponse;
import kg.itacademy.sewerfactory.entity.Department;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DepartmentMapper {
    DepartmentMapper INSTANCE = Mappers.getMapper(DepartmentMapper.class);
    Department toDepartmentEntity(DepartmentRequest departmentRequest);
    DepartmentResponse toDepartmentResponse(Department department);
    List<DepartmentResponse> toDepartmentsResponse(List<Department> entities);
}
