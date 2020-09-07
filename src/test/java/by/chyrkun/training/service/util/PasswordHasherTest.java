package by.chyrkun.training.service.util;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class PasswordHasherTest {
    @Test
    void testHashGeneration() {
        String password1 = "Password";
        String password2 = "Password2";
        String password3 = new String("Password");
        assertArrayEquals(PasswordHasher.getHash(password1), PasswordHasher.getHash(password3));
        assertFalse(Arrays.equals(PasswordHasher.getHash(password1), PasswordHasher.getHash(password2)));
    }
}