package co.com.cristiancabarcas.model.commons.errors;

public class InvalidSalaryException extends RuntimeException {
    public InvalidSalaryException(String message) {
        super(message);
    }
}
