package co.com.cristiancabarcas.model.role.gateways;

import co.com.cristiancabarcas.model.role.Role;
import reactor.core.publisher.Mono;

public interface RoleRepository {

    Mono<Role> findById(Integer id);
}
