package kg.itacademy.sewerfactory.service;

import kg.itacademy.sewerfactory.dto.user.request.UserAuthRequest;
import kg.itacademy.sewerfactory.dto.user.request.UserRequest;
import kg.itacademy.sewerfactory.dto.user.request.UserUpdateRequest;
import kg.itacademy.sewerfactory.dto.user.response.UserResponse;
import kg.itacademy.sewerfactory.exception.UserSignInException;
import kg.itacademy.sewerfactory.model.AuthModel;

public interface UserService extends BaseService<UserResponse, UserRequest>{
    UserResponse findByToken();
    AuthModel auth(UserAuthRequest request) throws UserSignInException;
    Boolean updateUser(UserUpdateRequest t);
}
