package co.com.cristiancabarcas.model.commons.errors;

import co.com.cristiancabarcas.model.commons.DomainError;

public class InvalidFieldException extends RuntimeException {
    public InvalidFieldException(String fieldName) {
        super(String.format(DomainError.INVALID_FIELD.getMessage(), fieldName));
    }
}
