package br.com.gabriel.api.security;

import br.com.gabriel.api.util.HashUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence password) {
        return HashUtil.getSecureHash(password.toString());
    }

    @Override
    public boolean matches(CharSequence password, String passwordEncoded) {
        return HashUtil.getSecureHash(password.toString()).equals(passwordEncoded);
    }
}
