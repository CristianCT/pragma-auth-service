package co.com.cristiancabarcas.api.mapper;


import co.com.cristiancabarcas.api.dtos.user.UserRequest;
import co.com.cristiancabarcas.api.dtos.user.UserResponse;
import co.com.cristiancabarcas.model.user.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toModel(UserRequest userRequest);
    UserResponse toResponse(User user);
}
