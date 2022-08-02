package kg.itacademy.sewerfactory.service.impl;

import kg.itacademy.sewerfactory.dto.role.request.RoleRequest;
import kg.itacademy.sewerfactory.dto.role.response.RoleResponse;
import kg.itacademy.sewerfactory.exception.NotUniqueRecord;
import kg.itacademy.sewerfactory.mapper.RoleMapper;
import kg.itacademy.sewerfactory.repository.RoleRepository;
import kg.itacademy.sewerfactory.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleServiceImpl implements RoleService {
    final RoleRepository roleRepository;

    @Override
    public RoleResponse save(RoleRequest roleRequest) {
        try {
            roleRequest.setRoles(roleRequest.getRoles());
            return RoleMapper.INSTANCE
                    .toResponseDto(roleRepository.save(RoleMapper.INSTANCE.toRoleEntity(roleRequest)));
        } catch (Exception ignored) {
            throw new NotUniqueRecord("Одинноковая роль", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public List<RoleResponse> getAll() {
        return RoleMapper.INSTANCE.toRolesDto(roleRepository.findAll());
    }

    @Override
    public RoleResponse findById(Long id) {
        return RoleMapper.INSTANCE.toResponseDto(roleRepository.getById(id));
    }

    @Override
    public RoleResponse delete(Long id) {
        return null;
    }
}