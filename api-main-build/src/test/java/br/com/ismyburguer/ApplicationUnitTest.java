package br.com.ismyburguer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

class ApplicationUnitTest {

    @Test
    void deveDarErroAoCriarManualmente() {
        try {
            Application.main(new String[]{});
            fail();
        } catch (Exception e) {
            assertNotNull(e.getMessage());
        }

    }

}