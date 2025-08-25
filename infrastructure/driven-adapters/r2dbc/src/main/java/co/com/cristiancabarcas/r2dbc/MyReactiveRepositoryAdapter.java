package co.com.cristiancabarcas.r2dbc;

import co.com.cristiancabarcas.model.user.User;
import co.com.cristiancabarcas.model.user.gateways.UserRespository;

import co.com.cristiancabarcas.r2dbc.entities.UserEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Repository
public class MyReactiveRepositoryAdapter implements UserRespository {

    private final MyReactiveRepository repository;

    public MyReactiveRepositoryAdapter(MyReactiveRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public Mono<User> save(User user) {

        UserEntity userEntity = UserEntity.builder()
                .name(user.getName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .address(user.getAddress())
                .birthDate(user.getBirthDate())
                .salary(user.getSalary())
                .build();

        return repository.save(userEntity)
                .map(userSaved-> User.builder()
                        .name(userSaved.getName())
                        .lastName(userSaved.getLastName())
                        .email(userSaved.getEmail())
                        .phone(userSaved.getPhone())
                        .address(userSaved.getAddress())
                        .birthDate(userSaved.getBirthDate())
                        .salary(userSaved.getSalary())
                        .build());
    }

    @Transactional
    @Override
    public Mono<User> findByEmail(String email) {
        return Mono.empty();
    }

}
