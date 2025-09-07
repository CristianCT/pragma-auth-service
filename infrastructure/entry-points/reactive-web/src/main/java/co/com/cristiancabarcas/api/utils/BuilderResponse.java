package co.com.cristiancabarcas.api.utils;

import co.com.cristiancabarcas.api.dtos.CustomResponse;
import co.com.cristiancabarcas.api.dtos.auth.AuthResponse;
import co.com.cristiancabarcas.api.dtos.user.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BuilderResponse {

    public static ResponseEntity<CustomResponse<UserResponse>> buildCreatedUser (UserResponse userResponse) {
        return ResponseEntity.ok(CustomResponse.<UserResponse>builder()
                .data(userResponse)
                .message("User created successfully")
                .success(true)
                .build());
    }

    public static ResponseEntity<CustomResponse<AuthResponse>> buildAuthUser (AuthResponse authResponse) {
        return ResponseEntity.ok(CustomResponse.<AuthResponse>builder()
                .data(authResponse)
                .message("User authenticated successfully")
                .success(true)
                .build());
    }

    public static ResponseEntity<CustomResponse<Void>> buildErrorResponse (String message) {
        return ResponseEntity.badRequest().body(CustomResponse.<Void>builder()
                .message(message)
                .success(false)
                .build());
    }

    public static ResponseEntity<CustomResponse<Void>> buildUnauthorizedResponse (String message) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(CustomResponse.<Void>builder()
                .message(message)
                .success(false)
                .build());
    }
}
