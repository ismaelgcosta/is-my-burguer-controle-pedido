package br.com.ismyburguer.core.adapter.out;

import feign.Client;
import feign.Feign;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class FeignClientAPITest {
    private OAuth2ClientCredentialsFeignInterceptorAPI interceptor;
    private FeignClientAPI feignClientAPI;

    @BeforeEach
    void setUp() {
        interceptor = mock(OAuth2ClientCredentialsFeignInterceptorAPI.class);
        feignClientAPI = new FeignClientAPI(interceptor);
    }

    @BeforeAll
    public static void beforeAll() {
        mockStatic(Feign.class);
    }

    @Test
    void deveCriarClienteFeignCorretamente() {
        // Mocking dependencies
        Feign.Builder builderMock = mock(Feign.Builder.class);
        Feign feignMock = mock(Feign.class);

        when(Feign.builder()).thenReturn(builderMock);

        // Configurando comportamento do Feign.Builder
        when(builderMock.encoder(any())).thenReturn(builderMock);
        when(builderMock.decoder(any())).thenReturn(builderMock);
        when(builderMock.requestInterceptor(interceptor)).thenReturn(builderMock);

        // Configurando comportamento do Feign
        when(builderMock.client(any())).thenReturn(builderMock);

        // Configurando comportamento do Client.Default
        when(builderMock.target(any(), anyString())).thenReturn(feignMock);

        feignClientAPI.createClient(Feign.Builder.class);
    }
}
