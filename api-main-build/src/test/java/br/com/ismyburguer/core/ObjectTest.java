package br.com.ismyburguer.core;

import br.com.ismyburguer.core.validation.Validation;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.springframework.data.mongodb.core.mapping.Document;

class ObjectTest {

    @Test
    void deveRespeitarAsRegrasMinimas() {
        new Reflections("br.com.ismyburguer")
                .getTypesAnnotatedWith(Document.class)
                .forEach(aClass -> {
                    GetterSetterVerifier.forClass(aClass).verify();
                    EqualsVerifier.forClass(aClass).suppress(
                            Warning.STRICT_INHERITANCE,
                            Warning.INHERITED_DIRECTLY_FROM_OBJECT,
                            Warning.ALL_FIELDS_SHOULD_BE_USED,
                            Warning.BIGDECIMAL_EQUALITY,
                            Warning.NONFINAL_FIELDS).verify();
                });

        new Reflections("br.com.ismyburguer")
                .getSubTypesOf(Validation.class)
                .forEach(aClass -> {
                    GetterSetterVerifier.forClass(aClass).verify();
                    EqualsVerifier.forClass(aClass).suppress(
                                    Warning.STRICT_INHERITANCE,
                                    Warning.INHERITED_DIRECTLY_FROM_OBJECT,
                                    Warning.BIGDECIMAL_EQUALITY,
                                    Warning.ALL_FIELDS_SHOULD_BE_USED,
                                    Warning.NONFINAL_FIELDS)
                            .verify();
                });
    }

}