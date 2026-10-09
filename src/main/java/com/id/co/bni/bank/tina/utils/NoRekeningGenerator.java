package com.id.co.bni.bank.tina.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class NoRekeningGenerator {
    private NoRekeningGenerator() {
    }

    public static String generate(
            String nomorKTP,
            String nomorHandphone) {

        if (nomorKTP == null || !nomorKTP.matches("\\d{16}")) {
            throw new IllegalArgumentException(
                "Nomor KTP must contain exactly 16 digits"
            );
        }

        if (nomorHandphone == null
                || !nomorHandphone.matches("\\d{10,13}")) {
            throw new IllegalArgumentException(
                "Nomor handphone must contain 10-13 digits"
            );
        }

        try {
            MessageDigest digest =
            MessageDigest.getInstance("SHA-256");

            String input = nomorKTP + ":" + nomorHandphone;

            byte[] hash = digest.digest(
                input.getBytes(StandardCharsets.UTF_8)
            );

            long number = 0;

            for (int i = 0; i < 8; i++) {
                number = (number << 8) | (hash[i] & 0xffL);
            }

            number &= Long.MAX_VALUE;

            return String.format(
                "%09d",
                number % 1_000_000_000L
            );

        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(
                "SHA-256 algorithm is unavailable", e
            );
        }
    }
}
