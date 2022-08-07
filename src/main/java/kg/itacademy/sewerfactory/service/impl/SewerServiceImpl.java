package kg.itacademy.sewerfactory.service.impl;


import kg.itacademy.sewerfactory.dto.department.response.DepartmentResponse;
import kg.itacademy.sewerfactory.dto.sewer.request.SewerRequest;
import kg.itacademy.sewerfactory.dto.sewer.request.SewerUpdateRequest;
import kg.itacademy.sewerfactory.dto.sewer.response.SewerResponse;
import kg.itacademy.sewerfactory.entity.Department;
import kg.itacademy.sewerfactory.entity.Order;
import kg.itacademy.sewerfactory.entity.Sewer;
import kg.itacademy.sewerfactory.entity.User;
import kg.itacademy.sewerfactory.enums.Status;
import kg.itacademy.sewerfactory.exception.*;
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
import java.util.List;
import java.util.stream.Collectors;

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
                .status(Status.WAITING)
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
    public List<SewerResponse> getAll() throws NullPointerException{
        return sewerRepository.findAll().stream()
                .map(sewer -> SewerResponse.builder()
                        .status(sewer.getStatus())
                        .phoneNumber(sewer.getPhoneNumber())
                        .unitPrice(sewer.getOrder() == null ? null : sewer.getOrder().getUnitPrice())
                        .id(sewer.getId())
                        .departmentName(sewer.getDepartment().getDepartmentName())
                        .needAmount(sewer.getNeedAmount())
                        .fio(sewer.getFio())
                        .clothesType(sewer.getOrder() == null ? null : sewer.getOrder().getClothesType())
                        .email(sewer.getUser().getEmail())
                        .build()).collect(Collectors.toList());
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
    public SewerResponse delete(Long id){
        Sewer sewer = sewerRepository.getById(id);
        sewerRepository.delete(sewer);
        return SewerResponse.builder().build();
    }

    @Override
    public Boolean updateSewer(SewerUpdateRequest t) {
        Sewer sewer = sewerRepository.getById(t.getId());
        Order order = null;
        if (t.getOrderId() != null){
            order = orderRepository.getById(t.getOrderId());
        }
        Department department = departmentRepository.getById(t.getDepartmentId());
        if (department.getOrder() != null){
            sewer.setOrder(order);
            sewer.setNeedAmount(t.getNeedAmount());
        }else {
            sewer.setOrder(null);
            sewer.setNeedAmount(null);
        }
        sewer.setStatus(t.getStatus());
        sewer.setDepartment(department);
        sewer.setFio(t.getFio());
        sewer.setPhoneNumber(t.getPhoneNumber());
        if (sewer.getNeedAmount() < 0 || sewer.getNeedAmount() > order.getAmount()){
            throw new ImpossibleCaseException("Превышение указанного числа в заказе или отрицательное число", HttpStatus.BAD_REQUEST);
        }
        sewerRepository.save(sewer);
        return sewer.getId() != null;
    }

    @Override//доработать
    public BigDecimal countSewerSalary(Long id){
        Sewer sewer = sewerRepository.getById(id);
        if (sewer.getStatus().equals(Status.DONE)) {
            BigDecimal salary = BigDecimal.valueOf(sewer.getNeedAmount() * sewer.getOrder().getUnitPrice());
            sewer.setNeedAmount(0L);
            sewer.setStatus(Status.WAITING);
            sewerRepository.save(sewer);
            return salary;
        } else throw new NotFinishWorkException("Ещё не закончила работу", HttpStatus.BAD_REQUEST);
    }

    @Override
    public List<SewerResponse> getAllSewersByDepartmentId(Long id) {
        return sewerRepository.findAllSewersByDepartmentId(id).stream()
                .map(sewer -> SewerResponse.builder()
                        .status(sewer.getStatus())
                        .phoneNumber(sewer.getPhoneNumber())
                        .unitPrice(sewer.getOrder() == null ? null : sewer.getOrder().getUnitPrice())
                        .id(sewer.getId())
                        .departmentName(sewer.getDepartment().getDepartmentName())
                        .needAmount(sewer.getNeedAmount())
                        .fio(sewer.getFio())
                        .clothesType(sewer.getOrder() == null ? null : sewer.getOrder().getClothesType())
                        .email(sewer.getUser().getEmail())
                        .build()).collect(Collectors.toList());
    }
}
