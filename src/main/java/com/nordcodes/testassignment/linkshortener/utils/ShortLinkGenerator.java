package com.nordcodes.testassignment.linkshortener.utils;

import java.security.SecureRandom;

public class ShortLinkGenerator {

    private static final int SHORT_LINK_LENGTH = 6;
    private static final String ALPHABET = "abcdefghigklmnopqrstuvwxyz0123456789";

    public static String generate() {
        final SecureRandom random = new SecureRandom();
        char[] chars = new char[SHORT_LINK_LENGTH];
        for (int i = 0; i < chars.length; i++) {
            int randomPosition = random.nextInt(ALPHABET.length());
            chars[i] = ALPHABET.charAt(randomPosition);
        }
        return String.valueOf(chars);
    }
}
