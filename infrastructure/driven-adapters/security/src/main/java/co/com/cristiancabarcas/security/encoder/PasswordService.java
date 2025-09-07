package co.com.cristiancabarcas.security.encoder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {


    private final PasswordEncoder passwordEncoder;

    public PasswordService(@Value("${security.bcrypt.strength}") Integer strength) {
        this.passwordEncoder = new BCryptPasswordEncoder(strength);
    }

    public String encryptPassword(String rawPassword) {
        if (rawPassword == null || rawPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid password cannot be null or empty");
        }
        return passwordEncoder.encode(rawPassword);
    }


    public boolean comparePasswords(String password, String encodedPassword) {
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid password cannot be null or empty");
        }

        if (encodedPassword == null || encodedPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid encoded password cannot be null or empty");
        }

        return passwordEncoder.matches(password, encodedPassword);
    }
}
