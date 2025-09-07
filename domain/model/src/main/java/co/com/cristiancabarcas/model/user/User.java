package co.com.cristiancabarcas.model.user;

import co.com.cristiancabarcas.model.role.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {

    private String name;
    private String lastName;
    private String identificationNumber;
    private LocalDate birthDate;
    private String address;
    private String phone;
    private String email;
    private String password;
    private Double salary;
    private Role role;
}
