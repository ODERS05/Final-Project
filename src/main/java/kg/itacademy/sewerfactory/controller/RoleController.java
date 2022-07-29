package kg.itacademy.sewerfactory.controller;


import kg.itacademy.sewerfactory.dto.role.request.RoleRequest;
import kg.itacademy.sewerfactory.dto.role.response.RoleResponse;
import kg.itacademy.sewerfactory.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/role")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleController {
    final RoleService roleService;

    @PostMapping
    public RoleResponse save(@RequestBody RoleRequest request) {
        return roleService.save(request);
    }

    @GetMapping
    public List<RoleResponse> getAll() {
        return roleService.getAll();
    }

    @GetMapping("/{id}")
    public RoleResponse findById(@PathVariable Long id) {
        return roleService.findById(id);
    }
}
