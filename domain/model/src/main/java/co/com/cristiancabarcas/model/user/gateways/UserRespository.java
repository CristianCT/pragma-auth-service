package co.com.cristiancabarcas.model.user.gateways;

import co.com.cristiancabarcas.model.user.User;
import reactor.core.publisher.Mono;

public interface UserRespository {

    Mono<User> save(User user);
    Mono<User> findByEmail(String email);
}
