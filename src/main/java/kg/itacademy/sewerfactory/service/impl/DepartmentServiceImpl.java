package kg.itacademy.sewerfactory.service.impl;

import kg.itacademy.sewerfactory.dto.department.request.DepartmentRequest;
import kg.itacademy.sewerfactory.dto.department.request.DepartmentUpdateRequest;
import kg.itacademy.sewerfactory.dto.department.response.DepartmentResponse;
import kg.itacademy.sewerfactory.dto.sewer.response.SewerResponse;
import kg.itacademy.sewerfactory.entity.Department;
import kg.itacademy.sewerfactory.entity.Order;
import kg.itacademy.sewerfactory.entity.Sewer;
import kg.itacademy.sewerfactory.enums.Status;
import kg.itacademy.sewerfactory.exception.NotUniqueDepartment;
import kg.itacademy.sewerfactory.exception.OrderNotFoundException;
import kg.itacademy.sewerfactory.mapper.DepartmentMapper;
import kg.itacademy.sewerfactory.repository.DepartmentRepository;
import kg.itacademy.sewerfactory.repository.OrderRepository;
import kg.itacademy.sewerfactory.repository.SewerRepository;
import kg.itacademy.sewerfactory.service.DepartmentService;
import kg.itacademy.sewerfactory.service.SewerService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DepartmentServiceImpl implements DepartmentService {
    final DepartmentRepository departmentRepository;
    final  OrderRepository orderRepository;
    @Override
    public DepartmentResponse save(DepartmentRequest t) {
        try {
            Department department = departmentRepository.save(Department.builder()
                    .departmentName(t.getDepartmentName())
                    .build());
            departmentRepository.save(department);
            return DepartmentResponse.builder()
                    .id(department.getId())
                    .departmentName(t.getDepartmentName())
                    .build();
        }catch (Exception ignored){
            throw  new NotUniqueDepartment("Одиннаковое название отделов", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public List<DepartmentResponse> getAll() {
        return DepartmentMapper.INSTANCE.toDepartmentsResponse(departmentRepository.findAll());
    }

    @Override
    public DepartmentResponse findById(Long id) {
        return DepartmentMapper.INSTANCE.toDepartmentResponse(departmentRepository.getById(id));
    }

    @Override
    public DepartmentResponse delete(Long id) {
        Department department = departmentRepository.getById(id);
        departmentRepository.delete(department);
        return DepartmentResponse.builder().build();
    }

    @Override
    public Boolean updateDepartment(DepartmentUpdateRequest t){
        Department department = departmentRepository.getById(t.getId());
        Order order = null;
        if(t.getOrderId()!=null){
             order = orderRepository.getById(t.getOrderId());
            order.setStatus(t.getStatus());
        }
        department.setDepartmentName(t.getDepartmentName());
        department.setOrder(order);
        departmentRepository.save(department);
        return department.getId() != null;
    }
}
