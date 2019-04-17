package com.tuliohdev.myapplication;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EmailValidatorTest {

    private EmailValidator emailValidator;

    @Before
    public void setUp() {
        emailValidator = new EmailValidator();
    }

    @Test
    public void isValidEmailWhenEmailIsValid() {
        // Given
        final String email = "tulio.magalhaes@zup.com.br";

        // When
        boolean actual = emailValidator.isValidEmail(email);

        // Then
        assertTrue(actual);
    }

    @Test
    public void isValidEmailWhenEmailIsNotValid() {
        // Given
        final String email = "tulio.magalhaes@*.br";

        // When
        boolean actual = emailValidator.isValidEmail(email);

        // Then
        assertFalse(actual);
    }

    @Test
    public void isValidEmailWhenEmailIsNull() {
        // Given
        final String email = null;

        // When
        boolean actual = emailValidator.isValidEmail(email);

        // Then
        assertFalse(actual);
    }

    @Test
    public void isValidEmailWhenEmailIsEmpty() {
        // Given
        final String email = "";

        // When
        boolean actual = emailValidator.isValidEmail(email);

        // Then
        assertFalse(actual);
    }
}