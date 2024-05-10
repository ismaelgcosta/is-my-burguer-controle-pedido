package br.com.ismyburguer.core.validation;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.*;
import jakarta.validation.constraints.NotNull;
import lombok.Setter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ValidationTest {
    private Dummy dummy;

    // Criando uma classe anônima simulada que implementa a interface Validation

    @Setter
    public static final class Dummy implements br.com.ismyburguer.core.validation.Validation {
        @NotNull
        private String notNull;
    };

    @BeforeEach
    void setUp() {
        dummy = new Dummy();
    }

    @Test
    void deveValidarSemViolacoes() {
        dummy.setNotNull("a");
        assertDoesNotThrow(() -> dummy.validate());
    }

    @Test
    void deveLancarExcecaoParaViolacoes() {
        ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, () -> dummy.validate());
        assertEquals(1, exception.getConstraintViolations().size());
        assertEquals("notNull", exception.getConstraintViolations().iterator().next().getPropertyPath().toString());
    }
}
