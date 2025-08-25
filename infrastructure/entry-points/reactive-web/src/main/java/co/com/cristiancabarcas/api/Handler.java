package co.com.cristiancabarcas.api;

import co.com.cristiancabarcas.api.dtos.CustomResponse;
import co.com.cristiancabarcas.api.dtos.user.UserRequest;
import co.com.cristiancabarcas.api.dtos.user.UserResponse;
import co.com.cristiancabarcas.api.mapper.UserMapper;
import co.com.cristiancabarcas.usecase.saveuser.SaveUserUseCase;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class Handler {

    private final UserMapper userMapper;
    private final SaveUserUseCase saveUserUseCase;

    @PostMapping
    @Operation(summary = "Create new user", description = "Registers a new user in the system")
    public Mono<ResponseEntity<CustomResponse<UserResponse>>> createNewUser(@RequestBody UserRequest userRequest) {

        return saveUserUseCase.execute(userMapper.toModel(userRequest))
                .map(userMapper::toResponse)
                .map(userResponse -> ResponseEntity.ok(CustomResponse.<UserResponse>builder()
                        .data(userResponse)
                        .message("User created successfully")
                        .success(true)
                        .build()))
                .onErrorResume(e -> Mono.just(ResponseEntity.ok().body(CustomResponse.<UserResponse>builder()
                                .message(e.getMessage())
                                .success(false)
                        .build())));
    }

}
