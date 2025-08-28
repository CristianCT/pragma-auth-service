package co.com.cristiancabarcas.r2dbc;

import co.com.cristiancabarcas.model.user.User;
import co.com.cristiancabarcas.r2dbc.entities.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.utils.ObjectMapper;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MyReactiveRepositoryAdapterTest {

    @InjectMocks
    MyReactiveRepositoryAdapter repositoryAdapter;

    @Mock
    MyReactiveRepository repository;

    @Mock
    ObjectMapper mapper;

    private UserEntity userEntity;
    private User user;
    private final static LocalDate localDate = LocalDate.now();

    @Test
    void save() {

        user = User.builder()
                .name("name")
                .lastName("lastName")
                .email("c@gmail.com")
                .birthDate(localDate)
                .salary(1000000.0)
                .phone("1234567890")
                .address("address")
                .build();

        userEntity = UserEntity.builder()
                .name("name")
                .lastName("lastName")
                .email("c@gmail.com")
                .birthDate(localDate)
                .salary(1000000.0)
                .phone("1234567890")
                .address("address")
                .build();

        when(repository.save(any(UserEntity.class)))
                .thenReturn(Mono.just(userEntity));

        when(mapper.map(any(User.class), any()))
                .thenReturn(userEntity);

        when(mapper.map(any(UserEntity.class), any()))
                .thenReturn(user);

        StepVerifier.create(repositoryAdapter.save(user))
                .assertNext(result -> {
                    assertNotNull(result);
                    assertEquals("name", result.getName());
                    assertEquals("lastName", result.getLastName());
                    assertEquals("c@gmail.com", result.getEmail());
                    assertEquals(1000000.0, result.getSalary());
                    assertEquals(localDate, result.getBirthDate());
                    assertEquals("1234567890", result.getPhone());
                    assertEquals("address", result.getAddress());
                })
                .verifyComplete();

        verify(repository, times(1)).save(any(UserEntity.class));
        verify(mapper, times(1)).map(any(User.class), any());
        verify(mapper, times(1)).map(any(UserEntity.class), any());
    }

}
