package co.com.cristiancabarcas.api.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    private String name;
    private String lastName;
    private LocalDate birthDate;
    private String identificationNumber;
    private String address;
    private String phone;
    private String email;
    private String password;
    private Integer role;
    private Double salary;
}
