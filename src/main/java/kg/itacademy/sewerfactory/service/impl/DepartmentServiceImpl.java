package kg.itacademy.sewerfactory.service.impl;

import kg.itacademy.sewerfactory.dto.department.request.DepartmentRequest;
import kg.itacademy.sewerfactory.dto.department.response.DepartmentResponse;
import kg.itacademy.sewerfactory.dto.sewer.response.SewerResponse;
import kg.itacademy.sewerfactory.entity.Department;
import kg.itacademy.sewerfactory.entity.Order;
import kg.itacademy.sewerfactory.entity.Sewer;
import kg.itacademy.sewerfactory.exception.NotUniqueDepartment;
import kg.itacademy.sewerfactory.exception.OrderNotFoundException;
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
    final SewerService sewerService;
    ModelMapper modelMapper;
    final SewerRepository sewerRepository;
    final OrderRepository orderRepository;
    @Override
    public DepartmentResponse save(DepartmentRequest t) {
        try {
            Order order = orderRepository.findById(t.getOrderId()).orElseThrow(() -> new OrderNotFoundException("Такого заказа нет", HttpStatus.BAD_REQUEST));
            Department department = departmentRepository.save(Department.builder()
                    .departmentName(t.getDepartmentName())
                    .order(order)
                    .build());
            List<Sewer> sewers = new ArrayList<>();
            for (int i = 0; i < t.getSewerId().size(); i++) {
                sewers.add(sewerRepository.findById(t.getSewerId().get(i)).get());
            }
            department.setSewers(sewers);
            departmentRepository.save(department);
            Type listType = new TypeToken<List<SewerResponse>>(){}.getType();
            List<SewerResponse> sewerResponses = modelMapper.map(sewers,listType);
            return DepartmentResponse.builder()
                    .id(department.getId())
                    .sewers(sewerResponses)
                    .departmentName(t.getDepartmentName())
                    .clothType(order.getClothesType())
                    .unitPrice(order.getUnitPrice())
                    .amount(order.getAmount())
                    .status(order.getStatus())
                    .build();
        }catch (Exception ignored){
            throw  new NotUniqueDepartment("Одиннаковое название отделов", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public List<DepartmentResponse> getAll() {
        List<Department> departments = departmentRepository.findAll();
        List<DepartmentResponse> departmentResponses = new ArrayList<>();
        for (Department department: departments) {
            departmentResponses.add(DepartmentResponse.builder()
                    .sewers(sewerService.getAll())
                    .departmentName(department.getDepartmentName())
                    .id(department.getId())
                    .amount(department.getOrder().getAmount())
                    .unitPrice(department.getOrder().getUnitPrice())
                    .clothType(department.getOrder().getClothesType())
                    .status(department.getOrder().getStatus())
                    .build());
        }return departmentResponses;
    }

    @Override
    public DepartmentResponse findById(Long id) {
        Department department = departmentRepository.getById(id);
        List<Sewer> sewers = sewerRepository.findAll();
        List<SewerResponse> sewerResponses = new ArrayList<>();
        for (Sewer sewer : sewers) {
            sewerResponses.add(SewerResponse.builder()
                    .status(sewer.getStatus())
                    .needAmount(sewer.getNeedAmount())
                    .clothesType(sewer.getOrder().getClothesType())
                    .fio(sewer.getFio())
                    .unitPrice(sewer.getOrder().getUnitPrice())
                    .id(sewer.getId())
                    .build());
        }
        return DepartmentResponse.builder()
                .id(department.getId())
                .sewers(sewerResponses)
                .departmentName(department.getDepartmentName())
                .clothType(department.getOrder().getClothesType())
                .unitPrice(department.getOrder().getUnitPrice())
                .amount(department.getOrder().getAmount())
                .build();
    }
}
