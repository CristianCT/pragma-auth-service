package co.com.cristiancabarcas.model.commons;

import lombok.Getter;

@Getter
public enum DomainError {
    USER_ALREADY_EXIST("User already exist", "User already exists"),
    INVALID_FIELD("Invalid field", "The field %s is required and must not be empty"),
    INVALID_SALARY("Invalid salary", "Salary must be greater than zero and less than 15000000");

    private final String type;
    private final String message;

    DomainError(String type, String message) {
        this.type = type;
        this.message = message;
    }
}
