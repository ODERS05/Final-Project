package kg.itacademy.sewerfactory.service.impl;


import kg.itacademy.sewerfactory.dto.department.response.DepartmentResponse;
import kg.itacademy.sewerfactory.dto.order.response.OrderResponse;
import kg.itacademy.sewerfactory.dto.sewer.request.SewerRequest;
import kg.itacademy.sewerfactory.dto.sewer.request.SewerUpdateRequest;
import kg.itacademy.sewerfactory.dto.sewer.response.SewerResponse;
import kg.itacademy.sewerfactory.dto.user.request.UserRequest;
import kg.itacademy.sewerfactory.entity.Department;
import kg.itacademy.sewerfactory.entity.Order;
import kg.itacademy.sewerfactory.entity.Sewer;
import kg.itacademy.sewerfactory.entity.User;
import kg.itacademy.sewerfactory.exception.DepartmentNotFoundException;
import kg.itacademy.sewerfactory.exception.ImpossibleCaseException;
import kg.itacademy.sewerfactory.exception.OrderNotFoundException;
import kg.itacademy.sewerfactory.exception.UserNotFoundException;
import kg.itacademy.sewerfactory.repository.DepartmentRepository;
import kg.itacademy.sewerfactory.repository.OrderRepository;
import kg.itacademy.sewerfactory.repository.SewerRepository;
import kg.itacademy.sewerfactory.repository.UserRepository;
import kg.itacademy.sewerfactory.service.SewerService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class SewerServiceImpl implements SewerService {
    final SewerRepository sewerRepository;
    final OrderRepository orderRepository;
    final DepartmentRepository departmentRepository;
    final UserRepository userRepository;

    @Override
    public SewerResponse save(SewerRequest t) {
        User user = userRepository.findById(t.getUserId()).orElseThrow(() -> new UserNotFoundException("Такого пользователя нет", HttpStatus.BAD_REQUEST));
        Department department = departmentRepository.findById(t.getDepartmentId()).orElseThrow(() -> new DepartmentNotFoundException("Такого отедал нет", HttpStatus.BAD_REQUEST));
        Sewer sewer = sewerRepository.save(Sewer.builder()
                .id(user.getId())
                .department(department)
                .phoneNumber(t.getPhoneNumber())
                .fio(t.getFio())
                .user(user)
                .status("Waiting")
                .build());
        DepartmentResponse departmentResponse = DepartmentResponse.builder()
                .departmentName(department.getDepartmentName())
                .build();
        return SewerResponse.builder()
                .fio(sewer.getFio())
                .phoneNumber(sewer.getPhoneNumber())
                .departmentName(departmentResponse.getDepartmentName())
                .id(sewer.getId())
                .status(sewer.getStatus())
                .email(sewer.getUser().getEmail())
                .build();
    }

    @Override
    public List<SewerResponse> getAll() {

        List<Sewer> sewers = sewerRepository.findAll();
        List<SewerResponse> sewerResponses = new ArrayList<>();
        for (Sewer sewer : sewers) {
            sewerResponses.add(SewerResponse.builder()
                    .status(sewer.getStatus())
                    .phoneNumber(sewer.getPhoneNumber())
                    .unitPrice(sewer.getOrder().getUnitPrice())
                    .id(sewer.getId())
                    .departmentName(sewer.getDepartment().getDepartmentName())
                    .needAmount(sewer.getNeedAmount())
                    .fio(sewer.getFio())
                    .clothesType(sewer.getOrder().getClothesType())
                    .email(sewer.getUser().getEmail())
                    .build());
        }
        return sewerResponses;
    }

    @Override
    public SewerResponse findById(Long id) {
        Sewer sewer = sewerRepository.getById(id);
        return SewerResponse.builder()
                .id(sewer.getId())
                .fio(sewer.getFio())
                .phoneNumber(sewer.getPhoneNumber())
                .clothesType(sewer.getOrder().getClothesType())
                .needAmount(sewer.getNeedAmount())
                .departmentName(sewer.getDepartment().getDepartmentName())
                .status(sewer.getStatus())
                .unitPrice(sewer.getOrder().getUnitPrice())
                .email(sewer.getUser().getEmail())
                .build();

    }

    @Override
    public Boolean updateSewer(SewerUpdateRequest t) {
        Sewer sewer = sewerRepository.getById(t.getId());
        Order order = orderRepository.getById(t.getOrderId());
        sewer.setStatus(t.getStatus());
        sewer.setDepartment(Department.builder().departmentName(t.getDepartmentName()).build());
        sewer.setNeedAmount(t.getNeedAmount());
        sewer.setFio(t.getFio());
        sewer.setOrder(order);
        sewer.setPhoneNumber(t.getPhoneNumber());
        sewer.setUser(User.builder().email(t.getEmail()).build());
        if (sewer.getNeedAmount() < 0 || sewer.getNeedAmount() > order.getAmount()){
            throw new ImpossibleCaseException("Превышение указанного числа в заказе или отрицательное число", HttpStatus.BAD_REQUEST);
        }
        sewerRepository.save(sewer);
        return sewer.getId() != null;
    }

    @Override//доработать
    public BigDecimal countSewerSalary(Long id){
        Sewer sewer = sewerRepository.getById(id);
        if (sewer.getStatus().equals("Done")) {
            BigDecimal salary = BigDecimal.valueOf(sewer.getNeedAmount() * sewer.getOrder().getUnitPrice());
            sewer.setNeedAmount(0L);
            sewer.setStatus("Waiting");
            sewerRepository.save(sewer);
            return salary;
        } else return null;
    }
}
