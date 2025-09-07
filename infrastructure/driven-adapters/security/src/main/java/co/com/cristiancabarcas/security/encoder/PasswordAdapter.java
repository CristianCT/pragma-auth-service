package co.com.cristiancabarcas.security.encoder;

import co.com.cristiancabarcas.model.user.gateways.PasswordRepository;
import org.springframework.stereotype.Repository;

@Repository
public class PasswordAdapter implements PasswordRepository {

    private final PasswordService passwordService;

    public PasswordAdapter(PasswordService passwordService) {
        this.passwordService = passwordService;
    }

    @Override
    public String encrypt(String password) {
        return passwordService.encryptPassword(password);
    }

    @Override
    public boolean matches(String password, String encryptedPassword) {
        return passwordService.comparePasswords(password, encryptedPassword);
    }
}
