package co.com.cristiancabarcas.api;

import co.com.cristiancabarcas.api.dtos.CustomResponse;
import co.com.cristiancabarcas.api.dtos.auth.AuthResponse;
import co.com.cristiancabarcas.api.dtos.auth.CredentialsRequest;
import co.com.cristiancabarcas.api.dtos.user.UserRequest;
import co.com.cristiancabarcas.api.dtos.user.UserResponse;
import co.com.cristiancabarcas.api.utils.BuilderResponse;
import co.com.cristiancabarcas.model.user.User;
import co.com.cristiancabarcas.usecase.authuser.AuthUserUseCase;
import co.com.cristiancabarcas.usecase.saveuser.SaveUserUseCase;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class Handler {

    private final ObjectMapper mapper;
    private final SaveUserUseCase saveUserUseCase;
    private final AuthUserUseCase authUserUseCase;
    private static final Logger log = Logger.getLogger(Handler.class.getName());


    @PostMapping("/user")
    @Operation(summary = "Create new user", description = "Registers a new user in the system")
    public Mono<ResponseEntity<CustomResponse<UserResponse>>> createNewUser(@RequestBody UserRequest userRequest) {

        log.info("::::: INIT CREATE USER :::::");

        return saveUserUseCase.execute(mapper.map(userRequest, User.class), userRequest.getRole())
                .doOnNext(user -> log.info("User created success: " + user.getName() + " " + user.getLastName()))
                .map(user -> mapper.map(user, UserResponse.class))
                .map(BuilderResponse::buildCreatedUser);
    }

    @PostMapping("/login")
    @Operation(summary = "Auth user", description = "Auth user in the system")
    public Mono<ResponseEntity<CustomResponse<AuthResponse>>> auth(@RequestBody CredentialsRequest credentialsRequest) {

        log.info("::::: INIT CREATE USER :::::");

        return authUserUseCase.execute(credentialsRequest.getEmail(), credentialsRequest.getPassword())
                .doOnNext(auth -> log.info("User authenticated success: " + auth))
                .map(user -> AuthResponse.builder().token(user.getT1()).expiresIn(user.getT2()).build())
                .map(BuilderResponse::buildAuthUser);
    }
}
