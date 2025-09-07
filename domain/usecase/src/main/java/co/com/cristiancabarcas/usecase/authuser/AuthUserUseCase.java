package co.com.cristiancabarcas.usecase.authuser;

import co.com.cristiancabarcas.model.commons.DomainError;
import co.com.cristiancabarcas.model.commons.errors.IncorrectPasswordException;
import co.com.cristiancabarcas.model.commons.errors.InvalidFieldException;
import co.com.cristiancabarcas.model.commons.errors.UserNotFoundException;
import co.com.cristiancabarcas.model.role.gateways.RoleRepository;
import co.com.cristiancabarcas.model.user.User;
import co.com.cristiancabarcas.model.user.gateways.JwtAuthRepository;
import co.com.cristiancabarcas.model.user.gateways.PasswordRepository;
import co.com.cristiancabarcas.model.user.gateways.UserRespository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.Date;
import java.util.Optional;
import java.util.logging.Logger;

@RequiredArgsConstructor
public class AuthUserUseCase {

    private final UserRespository userRespository;
    private final RoleRepository roleRepository;
    private final PasswordRepository passwordRepository;
    private final JwtAuthRepository jwtAuthRepository;
    private static final Logger log = Logger.getLogger(AuthUserUseCase.class.getName());

    public Mono<Tuple2<String, Date>> execute(String email, String password) {

        return Mono.just(validateCredentials(email, password))
                .filter(isValid -> isValid.equals(true))
                .flatMap(isValid -> userRespository.findByEmail(email))
                .filter(user -> validatePassword(user.getT1(), password))
                .flatMap(user -> roleRepository.findById(user.getT2())
                        .flatMap(role -> Mono.zip(Mono.just(user.getT1()), Mono.just(role))))
                .flatMap(user -> jwtAuthRepository
                        .generateToken(user.getT1().getIdentificationNumber(), user.getT2().getName()))
                .switchIfEmpty(Mono.error(new UserNotFoundException(String.format(DomainError.USER_NOT_FOUND.getMessage(), email))))
                .onErrorResume(Mono::error);
    }

    private boolean validatePassword(User user, String password) {

        if (!passwordRepository.matches(password, user.getPassword())) {
            log.warning(DomainError.INCORRECT_PASSWORD.getMessage());
            throw new IncorrectPasswordException(DomainError.INCORRECT_PASSWORD.getMessage());
        }

        return true;
    }

    private boolean validateCredentials(String email, String password) {

        if (Optional.ofNullable(email).isEmpty() || email.isBlank()) {
            log.warning(String.format(DomainError.INVALID_FIELD.getMessage(), "email"));
            throw new InvalidFieldException("email");
        }

        if (Optional.ofNullable(password).isEmpty() || password.isBlank()) {
            log.warning(String.format(DomainError.INVALID_FIELD.getMessage(), "password"));
            throw new InvalidFieldException("password");
        }

        return true;
    }
}
