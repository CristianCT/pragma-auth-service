package co.com.cristiancabarcas.model.user.gateways;

import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.Date;

public interface JwtAuthRepository {
    Mono<Tuple2<String, Date>> generateToken(String userId, String role);
}
