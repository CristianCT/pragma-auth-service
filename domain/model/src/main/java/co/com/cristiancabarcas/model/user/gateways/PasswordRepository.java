package co.com.cristiancabarcas.model.user.gateways;

public interface PasswordRepository {

    String encrypt(String password);
    boolean matches(String password, String encryptedPassword);
}
