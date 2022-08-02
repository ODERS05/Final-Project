package kg.itacademy.sewerfactory.controller;

import kg.itacademy.sewerfactory.dto.user.request.UserAuthRequest;
import kg.itacademy.sewerfactory.dto.user.request.UserRequest;
import kg.itacademy.sewerfactory.dto.user.request.UserUpdateRequest;
import kg.itacademy.sewerfactory.dto.user.response.UserResponse;
import kg.itacademy.sewerfactory.service.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@CrossOrigin(origins = "*", maxAge = 8600)
public class UserController {
    final UserService userService;

    @PostMapping("/register")
    public UserResponse register(@RequestBody UserRequest request) {
        return userService.save(request);
    }

    @SneakyThrows
    @PostMapping("/auth")
    public String auto(@RequestBody UserAuthRequest request) {
        return userService.getToken(request);
    }

    @GetMapping
    public List<UserResponse> getAll(){
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public UserResponse findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PutMapping("/{id}")
    public Boolean update(@RequestBody UserUpdateRequest request, @PathVariable Long id){
        request.setId(id);
        return userService.updateUser(request);
    }

    @DeleteMapping("/{id}")
    public UserResponse delete(@PathVariable Long id){
        return userService.delete(id);
    }
    @GetMapping("/find-user")
    public UserResponse findByToken(){
        return userService.findByToken();
    }
}
