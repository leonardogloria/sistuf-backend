package org.br.sistufbackend.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/**
 * Encoder de Password Customizado, para o Algoritmo Sha-512 solicitado.
 */
@RequiredArgsConstructor
public class CustomPasswordEncoder implements PasswordEncoder {

    private final String passwordSalt;

    @Override
    public String encode(CharSequence rawPassword) {
        MessageDigest md = this.getMd();
        return String.format("%0128x", new BigInteger(1, md.digest(rawPassword.toString().getBytes(StandardCharsets.UTF_8))));
//        return new String(encodedPassword, StandardCharsets.UTF_8);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String encodedRawPassword = this.encode(rawPassword);
        return Objects.equals(encodedRawPassword, encodedPassword);
    }

    private MessageDigest getMd() {
        final String algorithm = "SHA-512";
        try  {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(this.passwordSalt.getBytes());
            return md;
        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalArgumentException("Algoritmo inválido para encoder: " + algorithm);
        }

    }
}
