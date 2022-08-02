package kg.itacademy.sewerfactory.controller;

import kg.itacademy.sewerfactory.dto.department.response.DepartmentResponse;
import kg.itacademy.sewerfactory.dto.sewer.request.SewerRequest;
import kg.itacademy.sewerfactory.dto.sewer.request.SewerUpdateRequest;
import kg.itacademy.sewerfactory.dto.sewer.response.SewerResponse;
import kg.itacademy.sewerfactory.service.SewerService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/sewer")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@CrossOrigin(origins = "*", maxAge = 8600)
public class SewerController {
    final SewerService sewerService;

    @PostMapping("/add-sewer")
    public SewerResponse save(@RequestBody SewerRequest request){
        return sewerService.save(request);
    }
    @GetMapping
    public List<SewerResponse> getAll(){
        return sewerService.getAll();
    }
    @PutMapping("/{id}")
    public Boolean update(@RequestBody SewerUpdateRequest request, @PathVariable Long id){
        request.setId(id);
        return sewerService.updateSewer(request);
    };
    @GetMapping("/{id}")
    public SewerResponse findById(@PathVariable Long id){
        return sewerService.findById(id);
    };

    @GetMapping("/count-salary/{id}")
    public BigDecimal countSewerSalary(@PathVariable Long id){
        return sewerService.countSewerSalary(id);
    }

    @GetMapping("/get-department-customer/{id}")
    public List<SewerResponse> getAllSewersByDepartmentId(@PathVariable Long id){
        return sewerService.getAllSewersByDepartmentId(id);
    }

    @DeleteMapping("/{id}")
    public SewerResponse delete(@PathVariable Long id){
        return sewerService.delete(id);
    }
}