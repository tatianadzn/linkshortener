package com.nordcodes.testassignment.linkshortener.config;

import com.nordcodes.testassignment.linkshortener.exceptions.TokenValidationException;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AuthenticationProvider {

    private static final String DELIMITER = "&";
    private static final String SHA_1 = "SHA-1";

    public void validateToken(final String token, final Map<String, String[]> parameters, final String secretKey)
            throws NoSuchAlgorithmException {
        final String constructedToken = constructToken(parameters, secretKey);

        if (!encryptSha1(constructedToken).equals(token.trim())) {
            throw new TokenValidationException("Token is not valid: " + token);
        }
    }

    private String constructToken(final Map<String, String[]> parameters, final String secretKey) {
        Set<Map.Entry<String, String[]>> entries = parameters.entrySet();

        return entries.stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> constructTokenSubstring(entry.getKey(), entry.getValue()))
                .collect(Collectors.joining(DELIMITER))
                .concat(secretKey);
    }

    public String constructTokenSubstring(String key, String[] values) {
        return Arrays.stream(values)
                .map(value -> key + "=" + value)
                .collect(Collectors.joining(DELIMITER));
    }

    public String encryptSha1(final String token) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(SHA_1);
        byte[] messageDigest = md.digest(token.getBytes());
        BigInteger no = new BigInteger(1, messageDigest);
        StringBuilder encoded = new StringBuilder(no.toString(16));

        while (encoded.length() < 32) {
            encoded.insert(0, "0");
        }
        return encoded.toString();
    }
}
