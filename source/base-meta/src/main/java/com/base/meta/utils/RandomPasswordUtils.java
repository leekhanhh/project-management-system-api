package com.base.meta.utils;

import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class RandomPasswordUtils {
    private String ALPHA_NUMERIC_STRING = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%&?{}*";
    private Integer POSTFIX_LENGTH = 8;

    private static final Set<String> passwordSet = new HashSet<>();

    public String createPassword() {
        String password;
        do {
            password = generateRandomPassword();
        } while (checkPasswordDuplicated(password));
        passwordSet.add(password);
        return password;
    }

    private String generateRandomPassword() {
        StringBuilder builder = new StringBuilder();
        builder.append("PMS-P@ssw0rd-");
        while (POSTFIX_LENGTH-- != 0) {
            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    private Boolean checkPasswordDuplicated(String password) {
        return !passwordSet.contains(password);
    }

}
