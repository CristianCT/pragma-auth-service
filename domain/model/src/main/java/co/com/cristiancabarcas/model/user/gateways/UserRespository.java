package co.com.cristiancabarcas.model.user.gateways;

import co.com.cristiancabarcas.model.user.User;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

public interface UserRespository {

    Mono<User> save(User user, Integer roleId);
    Mono<Tuple2<User, Integer>> findByEmail(String email);
}
