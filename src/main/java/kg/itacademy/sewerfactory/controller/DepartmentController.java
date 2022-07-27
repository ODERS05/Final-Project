package kg.itacademy.sewerfactory.controller;

import kg.itacademy.sewerfactory.dto.department.request.DepartmentRequest;
import kg.itacademy.sewerfactory.dto.department.response.DepartmentResponse;
import kg.itacademy.sewerfactory.service.DepartmentService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@CrossOrigin(origins = "*", maxAge = 8600)
public class DepartmentController {
    final DepartmentService departmentService;
    @PostMapping("/add-department")
    public DepartmentResponse addDepartment(@RequestBody DepartmentRequest request){
        return departmentService.save(request);
    }

    @GetMapping
    public List<DepartmentResponse> getAll(){
        return departmentService.getAll();
    }
    @GetMapping("/{id}")
    public DepartmentResponse findById(@PathVariable Long id) {
        return departmentService.findById(id);
    }

//    @PutMapping("/{id}")
//    public Boolean updateFloor(@RequestBody FloorUpdateRequest request, @PathVariable Long id){
//        request.setId(id);
//        return departmentService.updateFloor(request);
//    };
}