package kg.itacademy.sewerfactory.mapper;

import kg.itacademy.sewerfactory.dto.user.request.UserAuthRequest;
import kg.itacademy.sewerfactory.dto.user.request.UserRequest;
import kg.itacademy.sewerfactory.dto.user.response.UserResponse;
import kg.itacademy.sewerfactory.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserResponse toUserResponse(User user);

    List<UserResponse> toUsersResponse(List<User> users);

    UserAuthRequest toUserAuth(User user);

    User toUserEntity(UserRequest userRequest);

    default void test(UserRequest userRequest){

    }
}
