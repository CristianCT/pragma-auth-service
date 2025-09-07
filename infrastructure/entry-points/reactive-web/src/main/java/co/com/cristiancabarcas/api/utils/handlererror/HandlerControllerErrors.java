package co.com.cristiancabarcas.api.utils.handlererror;

import co.com.cristiancabarcas.api.dtos.CustomResponse;
import co.com.cristiancabarcas.api.utils.BuilderResponse;
import co.com.cristiancabarcas.model.commons.errors.IncorrectPasswordException;
import co.com.cristiancabarcas.model.commons.errors.InvalidFieldException;
import co.com.cristiancabarcas.model.commons.errors.InvalidSalaryException;
import co.com.cristiancabarcas.model.commons.errors.UserAlreadyExistException;
import co.com.cristiancabarcas.model.commons.errors.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.logging.Logger;

@RestControllerAdvice()
public class HandlerControllerErrors {

    private static final Logger log = Logger.getLogger(HandlerControllerErrors.class.getName());

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<CustomResponse<Void>> handleException(UserAlreadyExistException ex) {
        log.warning(ex.getMessage());
        return BuilderResponse.buildErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(InvalidFieldException.class)
    public ResponseEntity<CustomResponse<Void>> handleException(InvalidFieldException ex) {
        log.warning(ex.getMessage());
        return BuilderResponse.buildErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(InvalidSalaryException.class)
    public ResponseEntity<CustomResponse<Void>> handleException(InvalidSalaryException ex) {
        log.warning(ex.getMessage());
        return BuilderResponse.buildErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<CustomResponse<Void>> handleException(IncorrectPasswordException ex) {
        log.warning(ex.getMessage());
        return BuilderResponse.buildErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<CustomResponse<Void>> handleException(UserNotFoundException ex) {
        log.warning(ex.getMessage());
        return BuilderResponse.buildErrorResponse(ex.getMessage());
    }


}
