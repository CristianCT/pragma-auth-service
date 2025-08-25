package co.com.cristiancabarcas.usecase.saveuser;

import co.com.cristiancabarcas.model.user.gateways.UserRespository;
import co.com.cristiancabarcas.model.user.User;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.logging.Logger;

@RequiredArgsConstructor
public class SaveUserUseCase {

    private final  UserRespository userRepository;
    private static final Logger log = Logger.getLogger(SaveUserUseCase.class.getName());

    public Mono<User> execute(User user) {

        return Mono.just(user)
                .map(this::validUser)
                .flatMap(userRepository::save)
                .onErrorResume(Mono::error);
    }

    private User validUser(User user) {

        if (Optional.ofNullable(user).isEmpty()) {
            log.warning("El usuario no puede ser nulo");
            throw new IllegalArgumentException("El usuario no puede ser nulo");
        }

        if (Optional.ofNullable(user.getName()).isEmpty() || user.getName().isBlank()) {
            log.warning("El nombre no puede ser nulo o vacío");
            throw new IllegalArgumentException("El nombre no puede ser nulo o vacío");
        }

        if (Optional.ofNullable(user.getLastName()).isEmpty() || user.getLastName().isBlank()) {
            log.warning("El apellido no puede ser nulo o vacío");
            throw new IllegalArgumentException("El apellido no puede ser nulo o vacío");
        }

        if (Optional.ofNullable(user.getEmail()).isEmpty() || user.getEmail().isBlank()) {
            log.warning("El correo no puede ser nulo o vacío");
            throw new IllegalArgumentException("El correo no puede ser nulo o vacío");
        }

        if (Optional.ofNullable(user.getSalary()).isEmpty() || user.getSalary() <= 0 || user.getSalary() > 15000000) {
            log.warning("El salario no puede ser nulo o menor o igual a cero o mayoir a 15.000.000");
            throw new IllegalArgumentException("El salario no puede ser nulo o menor o igual a cero o mayoir a 15.000.000");
        }

        if (Optional.ofNullable(user.getPhone()).isEmpty() || user.getPhone().isBlank()) {
            log.warning("El teléfono no puede ser nulo o vacío");
            throw new IllegalArgumentException("El teléfono no puede ser nulo o vacío");
        }

        if (Optional.ofNullable(user.getAddress()).isEmpty() || user.getAddress().isBlank()) {
            log.warning("La dirección no puede ser nulo o vacío");
            throw new IllegalArgumentException("La dirección no puede ser nulo o vacío");
        }

        if (Optional.ofNullable(user.getBirthDate()).isEmpty()) {
            log.warning("La fecha de nacimiento no puede ser nulo o vacío");
            throw new IllegalArgumentException("La fecha de nacimiento no puede ser nulo o vacío");
        }

        return user;
    }
}
