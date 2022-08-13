package kg.itacademy.sewerfactory.service.impl;

import kg.itacademy.sewerfactory.dto.department.request.DepartmentRequest;
import kg.itacademy.sewerfactory.dto.department.request.DepartmentUpdateRequest;
import kg.itacademy.sewerfactory.dto.department.response.DepartmentResponse;
import kg.itacademy.sewerfactory.entity.Department;
import kg.itacademy.sewerfactory.entity.Order;
import kg.itacademy.sewerfactory.entity.Sewer;
import kg.itacademy.sewerfactory.enums.Status;
import kg.itacademy.sewerfactory.exception.NotUniqueDepartment;
import kg.itacademy.sewerfactory.repository.DepartmentRepository;
import kg.itacademy.sewerfactory.repository.OrderRepository;
import kg.itacademy.sewerfactory.repository.SewerRepository;
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
    final SewerRepository sewerRepository;
    @Override
    public DepartmentResponse save(DepartmentRequest t) {
        try {
            Department department = departmentRepository.save(Department.builder()
                    .departmentName(t.getDepartmentName())
                    .build());
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
        return departmentRepository.findAll().stream()
                .map(department -> DepartmentResponse.builder()
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
        Department department = departmentRepository.getById(id);
        return DepartmentResponse.builder()
                .departmentName(department.getDepartmentName())
                .clothType(department.getOrder() == null ? null : department.getOrder().getClothesType())
                .status(department.getOrder() == null ? null :department.getOrder().getStatus())
                .amount(department.getOrder() == null ? null : department.getOrder().getAmount())
                .unitPrice(department.getOrder() == null ? null : department.getOrder().getUnitPrice())
                .description(department.getOrder() == null ? null : department.getOrder().getDescription())
                .id(department.getId()).build();
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
        List<Sewer> sewers = sewerRepository.findAllSewersByDepartmentId(t.getId());
        Order order = null;
        if(t.getOrderId() != null && t.getOrderId() != 0){
            order = orderRepository.getById(t.getOrderId());
            order.setStatus(Status.INPROCESS);
            int sewersCount = sewers.size();
            for (Sewer sewer: sewers){
                sewer.setOrder(order);
                sewer.setNeedAmount(order.getAmount() / sewersCount);
                sewer.setStatus(Status.INPROCESS);
                sewerRepository.save(sewer);
            }
        }
        if (order == null){
            for (Sewer sewer: sewers) {
                sewer.setOrder(null);
            }
        }
        department.setOrder(order);
        if (t.getDepartmentName() != null){
            department.setDepartmentName(t.getDepartmentName());
        }
        departmentRepository.save(department);
        return department.getId() != null;
    }
}
