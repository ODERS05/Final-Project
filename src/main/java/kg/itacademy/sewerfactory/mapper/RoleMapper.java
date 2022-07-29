package kg.itacademy.sewerfactory.mapper;

import kg.itacademy.sewerfactory.dto.role.request.RoleRequest;
import kg.itacademy.sewerfactory.dto.role.response.RoleResponse;
import kg.itacademy.sewerfactory.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface RoleMapper {
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    Role toRoleEntity(RoleRequest roleRequestDto);

    Role toRoleEntity(RoleResponse roleResponseDto);

    RoleResponse toResponseDto(Role category);

    List<RoleResponse> toRolesDto(List<Role> categories);
}
