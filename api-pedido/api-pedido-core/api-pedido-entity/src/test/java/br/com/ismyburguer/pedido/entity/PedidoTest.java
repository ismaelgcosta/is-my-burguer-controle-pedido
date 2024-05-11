package br.com.ismyburguer.pedido.entity;

import io.github.benas.randombeans.EnhancedRandomBuilder;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

class PedidoTest {

    @Test
    void deveRespeitarAsRegrasMinimas() {
        EnhancedRandomBuilder.aNewEnhancedRandom().nextObject(Pedido.class);
        GetterSetterVerifier.forClass(Pedido.class).verify();
        EqualsVerifier.forClass(Pedido.class).suppress(
                Warning.STRICT_INHERITANCE,
                Warning.INHERITED_DIRECTLY_FROM_OBJECT,
                Warning.ALL_FIELDS_SHOULD_BE_USED,
                Warning.BIGDECIMAL_EQUALITY,
                Warning.NONFINAL_FIELDS).verify();

        new Pedido(
                new Pedido.PedidoId(UUID.randomUUID()),
                null,
                Pedido.StatusPedido.FECHADO,
                BigDecimal.ONE);
    }

}