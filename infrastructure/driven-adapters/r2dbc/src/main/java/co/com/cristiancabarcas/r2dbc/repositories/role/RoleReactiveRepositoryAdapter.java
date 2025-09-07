package co.com.cristiancabarcas.r2dbc.repositories.role;

import co.com.cristiancabarcas.model.role.Role;
import co.com.cristiancabarcas.model.role.gateways.RoleRepository;
import co.com.cristiancabarcas.r2dbc.entities.RoleEntity;
import co.com.cristiancabarcas.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;

@Repository
public class RoleReactiveRepositoryAdapter
        extends ReactiveAdapterOperations<Role, RoleEntity, Integer, RoleReactiveRepository>
        implements RoleRepository {

    private static final Logger log = Logger.getLogger(RoleReactiveRepositoryAdapter.class.getName());

    public RoleReactiveRepositoryAdapter(RoleReactiveRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Role.class));
    }

    @Override
    @Transactional
    public Mono<Role> findById(Integer id) {

        log.info("Finding role by id: " + id);
        return repository.findById(id)
                .map(roleEntity -> mapper.map(roleEntity, Role.class))
                .onErrorResume(Mono::error);
    }
}
