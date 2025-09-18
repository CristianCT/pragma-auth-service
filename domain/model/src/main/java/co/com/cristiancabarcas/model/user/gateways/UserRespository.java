package co.com.cristiancabarcas.model.user.gateways;

import co.com.cristiancabarcas.model.user.User;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple3;

public interface UserRespository {

    Mono<User> save(User user, Integer roleId);
    Mono<Tuple3<String, String, Integer>> findByEmail(String email);
}
