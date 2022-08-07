package kg.itacademy.sewerfactory.service.impl;

import kg.itacademy.sewerfactory.dto.department.request.DepartmentRequest;
import kg.itacademy.sewerfactory.dto.department.request.DepartmentUpdateRequest;
import kg.itacademy.sewerfactory.dto.department.response.DepartmentResponse;
import kg.itacademy.sewerfactory.entity.Department;
import kg.itacademy.sewerfactory.entity.Order;
import kg.itacademy.sewerfactory.enums.Status;
import kg.itacademy.sewerfactory.exception.NotUniqueDepartment;
import kg.itacademy.sewerfactory.mapper.DepartmentMapper;
import kg.itacademy.sewerfactory.repository.DepartmentRepository;
import kg.itacademy.sewerfactory.repository.OrderRepository;
import kg.itacademy.sewerfactory.service.DepartmentService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
                    .departmentStatus(Status.WAITING)
                    .build());
            return DepartmentResponse.builder()
                    .id(department.getId())
                    .departmentName(t.getDepartmentName())
                    .departmentStatus(department.getDepartmentStatus())
                    .build();
        }catch (Exception ignored){
            throw  new NotUniqueDepartment("Одиннаковое название отделов", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public List<DepartmentResponse> getAll() {
        return departmentRepository.findAll().stream()
                .map(department -> DepartmentResponse.builder()
                        .departmentStatus(department.getDepartmentStatus())
                        .departmentName(department.getDepartmentName())
                        .clothType(department.getOrder() == null ? null : department.getOrder().getClothesType())
                        .status(department.getOrder() == null ? null :department.getOrder().getStatus())
                        .amount(department.getOrder() == null ? null : department.getOrder().getAmount())
                        .unitPrice(department.getOrder() == null ? null : department.getOrder().getUnitPrice())
                        .description(department.getOrder() == null ? null : department.getOrder().getDescription())
                        .id(department.getId()).build()).collect(Collectors.toList());
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
        Department department = departmentRepository.findByDepartmentName(t.getDepartmentName());
        Order order = null;
        if(t.getOrderId()!= 0 && t.getOrderId() != null){
            order = orderRepository.getById(t.getOrderId());
            order.setStatus(Status.INPROCESS);
        }
        department.setDepartmentName(t.getDepartmentName());
        department.setOrder(order);
        department.setDepartmentStatus(t.getDepartmentStatus());
        departmentRepository.save(department);
        return department.getId() != null;
    }
}
