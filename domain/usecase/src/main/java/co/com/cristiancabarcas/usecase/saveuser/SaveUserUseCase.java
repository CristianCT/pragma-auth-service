package co.com.cristiancabarcas.usecase.saveuser;

import co.com.cristiancabarcas.model.commons.DomainError;
import co.com.cristiancabarcas.model.commons.errors.InvalidFieldException;
import co.com.cristiancabarcas.model.commons.errors.InvalidSalaryException;
import co.com.cristiancabarcas.model.commons.errors.UserAlreadyExistException;
import co.com.cristiancabarcas.model.user.User;
import co.com.cristiancabarcas.model.user.gateways.PasswordRepository;
import co.com.cristiancabarcas.model.user.gateways.UserRespository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.logging.Logger;

@RequiredArgsConstructor
public class SaveUserUseCase {

    private final UserRespository userRepository;
    private final PasswordRepository passwordRepository;
    private static final Logger log = Logger.getLogger(SaveUserUseCase.class.getName());

    public Mono<User> execute(User user, Integer roleId) {
        log.info("::::: INIT SAVE USER :::::");
        return Mono.just(user)
                .map(this::validUser)
                .map(newUser -> newUser.toBuilder().password(passwordRepository.encrypt(user.getPassword())).build())
                .flatMap(newUser -> userRepository.save(newUser, roleId))
                .switchIfEmpty(Mono.error(new UserAlreadyExistException(DomainError.USER_ALREADY_EXIST.getMessage())))
                .onErrorResume(Mono::error);
    }


    private User validUser(User user) {

        if (Optional.ofNullable(user.getIdentificationNumber()).isEmpty() || user.getIdentificationNumber().isBlank()) {
            log.warning(String.format(DomainError.INVALID_FIELD.getMessage(), "identification number"));
            throw new InvalidFieldException("identification number");
        }

        if (Optional.ofNullable(user.getName()).isEmpty() || user.getName().isBlank()) {
            log.warning(String.format(DomainError.INVALID_FIELD.getMessage(), "name"));
            throw new InvalidFieldException("name");
        }

        if (Optional.ofNullable(user.getLastName()).isEmpty() || user.getLastName().isBlank()) {
            log.warning(String.format(DomainError.INVALID_FIELD.getMessage(), "lastName"));
            throw new InvalidFieldException("lastName");
        }

        if (Optional.ofNullable(user.getEmail()).isEmpty() || user.getEmail().isBlank()) {
            log.warning(String.format(DomainError.INVALID_FIELD.getMessage(), "email"));
            throw new InvalidFieldException("email");
        }

        if (Optional.ofNullable(user.getSalary()).isEmpty() || user.getSalary() <= 0 || user.getSalary() > 15000000) {
            log.warning(DomainError.INVALID_SALARY.getMessage());
            throw new InvalidSalaryException(DomainError.INVALID_SALARY.getMessage());
        }

        if (Optional.ofNullable(user.getPhone()).isEmpty() || user.getPhone().isBlank()) {
            log.warning(String.format(DomainError.INVALID_FIELD.getMessage(), "phone"));
            throw new InvalidFieldException("phone");
        }

        if (Optional.ofNullable(user.getAddress()).isEmpty() || user.getAddress().isBlank()) {
            log.warning(String.format(DomainError.INVALID_FIELD.getMessage(), "address"));
            throw new InvalidFieldException("address");
        }

        if (Optional.ofNullable(user.getBirthDate()).isEmpty()) {
            log.warning(String.format(DomainError.INVALID_FIELD.getMessage(), "birth date"));
            throw new InvalidFieldException("birth date");
        }

        if (Optional.ofNullable(user.getPassword()).isEmpty() || user.getPassword().isBlank()) {
            log.warning(String.format(DomainError.INVALID_FIELD.getMessage(), "password"));
            throw new InvalidFieldException("password");
        }

        log.info("User validation successful");
        return user;
    }
}
