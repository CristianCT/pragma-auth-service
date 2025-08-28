package co.com.cristiancabarcas.r2dbc;

import co.com.cristiancabarcas.model.user.User;
import co.com.cristiancabarcas.model.user.gateways.UserRespository;
import co.com.cristiancabarcas.r2dbc.entities.UserEntity;
import co.com.cristiancabarcas.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;

@Repository
public class MyReactiveRepositoryAdapter
        extends ReactiveAdapterOperations<User, UserEntity, String, MyReactiveRepository>
        implements UserRespository {

    private static final Logger log = Logger.getLogger(MyReactiveRepositoryAdapter.class.getName());


    public MyReactiveRepositoryAdapter(MyReactiveRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, User.class));
    }

    @Override
    @Transactional
    public Mono<User> save(User user) {

        UserEntity userEntity = mapper.map(user, UserEntity.class);

        log.info("Saving user: " + userEntity.getName());
        return repository.save(userEntity)
                .map(userSaved-> mapper.map(userSaved, User.class))
                .onErrorResume(throwable ->
                        DuplicateKeyException.class.equals(throwable.getClass())
                                ? Mono.empty()
                                : Mono.error(throwable));
    }

}
