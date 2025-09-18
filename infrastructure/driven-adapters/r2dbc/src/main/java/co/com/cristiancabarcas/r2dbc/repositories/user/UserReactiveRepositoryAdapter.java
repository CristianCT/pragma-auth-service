package co.com.cristiancabarcas.r2dbc.repositories.user;

import co.com.cristiancabarcas.model.user.User;
import co.com.cristiancabarcas.model.user.gateways.UserRespository;
import co.com.cristiancabarcas.r2dbc.entities.UserEntity;
import co.com.cristiancabarcas.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuple3;

import java.util.Optional;
import java.util.logging.Logger;

@Repository
public class UserReactiveRepositoryAdapter
        extends ReactiveAdapterOperations<User, UserEntity, String, UserReactiveRepository>
        implements UserRespository {

    private static final Logger log = Logger.getLogger(UserReactiveRepositoryAdapter.class.getName());

    public UserReactiveRepositoryAdapter(UserReactiveRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, User.class));
    }

    @Override
    @Transactional
    public Mono<User> save(User user, Integer roleId) {

        UserEntity userEntity = mapper.map(user, UserEntity.class);
        userEntity.setRoleId(Optional.ofNullable(roleId).orElse(2));

        log.info("Saving user: " + userEntity.getName());
        return repository.save(userEntity)
                .map(userSaved-> mapper.map(userSaved, User.class))
                .onErrorResume(throwable ->
                        DuplicateKeyException.class.equals(throwable.getClass())
                                ? Mono.empty()
                                : Mono.error(throwable));
    }

    @Override
    @Transactional
    public Mono<Tuple3<String, String, Integer>> findByEmail(String email) {

        log.info("Finding user by email: " + email);
        return repository.findByEmail(email)
                .flatMap(userEntity -> Mono.zip(
                        Mono.just(userEntity.getId()),
                        Mono.just(userEntity.getPassword()),
                        Mono.just(userEntity.getRoleId())))
                .onErrorResume(Mono::error);
    }

}
