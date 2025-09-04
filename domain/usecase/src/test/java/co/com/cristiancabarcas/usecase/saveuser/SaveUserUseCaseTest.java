package co.com.cristiancabarcas.usecase.saveuser;

import co.com.cristiancabarcas.model.commons.errors.InvalidFieldException;
import co.com.cristiancabarcas.model.commons.errors.InvalidSalaryException;
import co.com.cristiancabarcas.model.commons.errors.UserAlreadyExistException;
import co.com.cristiancabarcas.model.user.User;
import co.com.cristiancabarcas.model.user.gateways.UserRespository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
class SaveUserUseCaseTest {

    @Mock
    private UserRespository userRepository;

    @InjectMocks
    private SaveUserUseCase saveUserUseCase;

    private User user;
    private final static LocalDate localDate = LocalDate.now();

    @Test
    void execute() {

        user = User.builder()
                .name("name")
                .lastName("lastName")
                .identificationNumber("1234567890")
                .email("c@gmail.com")
                .birthDate(localDate)
                .salary(1000000.0)
                .phone("1234567890")
                .address("address")
                .password("TEST")
                .build();

        when(userRepository.save(any(User.class), any()))
                .thenReturn(Mono.just(user));

        StepVerifier.create(saveUserUseCase.execute(this.user, 1))
                .assertNext(result -> {
                    assertNotNull(result);
                    assertEquals("name", result.getName());
                    assertEquals("lastName", result.getLastName());
                    assertEquals("1234567890", result.getIdentificationNumber());
                    assertEquals("c@gmail.com", result.getEmail());
                    assertEquals(1000000.0, result.getSalary());
                    assertEquals(localDate, result.getBirthDate());
                    assertEquals("1234567890", result.getPhone());
                    assertEquals("address", result.getAddress());
                })
                .verifyComplete();

        verify(userRepository, times(1)).save(any(User.class), any());
    }

    @Test
    void executeUserAlreadyExists() {

        user = User.builder()
                .name("name")
                .lastName("lastName")
                .identificationNumber("1234567890")
                .email("c@gmail.com")
                .birthDate(localDate)
                .salary(1000000.0)
                .phone("1234567890")
                .address("address")
                .password("TEST")
                .build();

        when(userRepository.save(any(User.class), any()))
                .thenReturn(Mono.empty());

        StepVerifier.create(saveUserUseCase.execute(this.user, 1))
                .expectErrorMatches(UserAlreadyExistException.class::isInstance)
                .verify();

        verify(userRepository, times(1)).save(any(User.class), any());
    }

    @Test
    void executeInvalidFieldIdentificationNumber() {

        user = User.builder()
                .identificationNumber("")
                .build();

        StepVerifier.create(saveUserUseCase.execute(this.user, 1))
                .expectErrorMatches(InvalidFieldException.class::isInstance)
                .verify();

        user = User.builder()
                .build();

        StepVerifier.create(saveUserUseCase.execute(this.user, 1))
                .expectErrorMatches(InvalidFieldException.class::isInstance)
                .verify();

    }


    @Test
    void executeInvalidFieldName() {

        user = User.builder()
                .identificationNumber("1234567890")
                .name("")
                .build();

        StepVerifier.create(saveUserUseCase.execute(this.user, 1))
                .expectErrorMatches(InvalidFieldException.class::isInstance)
                .verify();

        user = User.builder()
                .identificationNumber("1234567890")
                .build();

        StepVerifier.create(saveUserUseCase.execute(this.user, 1))
                .expectErrorMatches(InvalidFieldException.class::isInstance)
                .verify();

    }

    @Test
    void executeInvalidFieldLastName() {

        user = User.builder()
                .identificationNumber("1234567890")
                .name("name")
                .lastName("")
                .build();

        StepVerifier.create(saveUserUseCase.execute(this.user, 1))
                .expectErrorMatches(InvalidFieldException.class::isInstance)
                .verify();

        user = User.builder()
                .identificationNumber("1234567890")
                .name("name")
                .build();

        StepVerifier.create(saveUserUseCase.execute(this.user, 1))
                .expectErrorMatches(InvalidFieldException.class::isInstance)
                .verify();

    }

    @Test
    void executeInvalidFieldEmail() {

        user = User.builder()
                .identificationNumber("1234567890")
                .name("name")
                .lastName("lastName")
                .email("")
                .build();

        StepVerifier.create(saveUserUseCase.execute(this.user, 1))
                .expectErrorMatches(InvalidFieldException.class::isInstance)
                .verify();

        user = User.builder()
                .identificationNumber("1234567890")
                .name("name")
                .lastName("lastName")
                .build();

        StepVerifier.create(saveUserUseCase.execute(this.user, 1))
                .expectErrorMatches(InvalidFieldException.class::isInstance)
                .verify();

    }

    @Test
    void executeInvalidFieldSalary() {

        user = User.builder()
                .identificationNumber("1234567890")
                .name("name")
                .lastName("lastName")
                .email("c@gmail.com")
                .salary(0.0)
                .build();

        StepVerifier.create(saveUserUseCase.execute(this.user, 1))
                .expectErrorMatches(InvalidSalaryException.class::isInstance)
                .verify();

        user = User.builder()
                .identificationNumber("1234567890")
                .name("name")
                .lastName("lastName")
                .email("c@gmail.com")
                .salary(15000001.0)
                .build();

        StepVerifier.create(saveUserUseCase.execute(this.user, 1))
                .expectErrorMatches(InvalidSalaryException.class::isInstance)
                .verify();

        user = User.builder()
                .identificationNumber("1234567890")
                .name("name")
                .lastName("lastName")
                .email("c@gmail.com")
                .build();

        StepVerifier.create(saveUserUseCase.execute(this.user, 1))
                .expectErrorMatches(InvalidSalaryException.class::isInstance)
                .verify();

    }

    @Test
    void executeInvalidFieldPhone() {

        user = User.builder()
                .identificationNumber("1234567890")
                .name("name")
                .lastName("lastName")
                .email("c@gmail.com")
                .salary(1000000.0)
                .phone("")
                .build();

        StepVerifier.create(saveUserUseCase.execute(this.user, 1))
                .expectErrorMatches(InvalidFieldException.class::isInstance)
                .verify();

        user = User.builder()
                .identificationNumber("1234567890")
                .name("name")
                .lastName("lastName")
                .email("c@gmail.com")
                .salary(1000000.0)
                .build();

        StepVerifier.create(saveUserUseCase.execute(this.user, 1))
                .expectErrorMatches(InvalidFieldException.class::isInstance)
                .verify();

    }

    @Test
    void executeInvalidFieldAddress() {

        user = User.builder()
                .identificationNumber("1234567890")
                .name("name")
                .lastName("lastName")
                .email("c@gmail.com")
                .salary(1000000.0)
                .phone("1234567890")
                .address("")
                .build();

        StepVerifier.create(saveUserUseCase.execute(this.user, 1))
                .expectErrorMatches(InvalidFieldException.class::isInstance)
                .verify();

        user = User.builder()
                .identificationNumber("1234567890")
                .name("name")
                .lastName("lastName")
                .email("c@gmail.com")
                .salary(1000000.0)
                .phone("1234567890")
                .build();

        StepVerifier.create(saveUserUseCase.execute(this.user, 1))
                .expectErrorMatches(InvalidFieldException.class::isInstance)
                .verify();

    }

    @Test
    void executeInvalidFieldBirthDate() {

        user = User.builder()
                .identificationNumber("1234567890")
                .name("name")
                .lastName("lastName")
                .email("c@gmail.com")
                .salary(1000000.0)
                .phone("1234567890")
                .address("address")
                .build();

        StepVerifier.create(saveUserUseCase.execute(this.user, 1))
                .expectErrorMatches(InvalidFieldException.class::isInstance)
                .verify();

    }

    @Test
    void executeInvalidFieldPassword() {

        user = User.builder()
                .identificationNumber("1234567890")
                .name("name")
                .lastName("lastName")
                .email("c@gmail.com")
                .salary(1000000.0)
                .phone("1234567890")
                .address("address")
                .birthDate(localDate)
                .password("")
                .build();

        StepVerifier.create(saveUserUseCase.execute(this.user, 1))
                .expectErrorMatches(InvalidFieldException.class::isInstance)
                .verify();

        user = User.builder()
                .identificationNumber("1234567890")
                .name("name")
                .lastName("lastName")
                .email("c@gmail.com")
                .salary(1000000.0)
                .phone("1234567890")
                .address("address")
                .birthDate(localDate)
                .build();

        StepVerifier.create(saveUserUseCase.execute(this.user, 1))
                .expectErrorMatches(InvalidFieldException.class::isInstance)
                .verify();

    }
}