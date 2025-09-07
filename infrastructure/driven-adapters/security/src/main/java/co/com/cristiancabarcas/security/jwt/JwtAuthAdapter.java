package co.com.cristiancabarcas.security.jwt;

import co.com.cristiancabarcas.model.user.gateways.JwtAuthRepository;
import org.springframework.stereotype.Repository;
import org.yaml.snakeyaml.util.Tuple;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.Date;

@Repository
public class JwtAuthAdapter implements JwtAuthRepository {

    private final JwtUtil jwtUtil;

    public JwtAuthAdapter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Tuple2<String, Date>> generateToken(String userId, String role) {
        Tuple<String, Date> tokenData = jwtUtil.generateToken(userId, role);
        return Mono.zip(Mono.just(tokenData._1()), Mono.just(tokenData._2()));
    }
}
