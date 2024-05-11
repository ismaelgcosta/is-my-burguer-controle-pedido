package br.com.ismyburguer.core.adapter.out;

import br.com.ismyburguer.core.Application;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import feign.Headers;
import feign.RequestLine;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import wiremock.com.fasterxml.jackson.databind.node.TextNode;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@SpringBootTest(classes = {Application.class, TestSecurityConfig.class})
@AutoConfigureWireMock(port = 0)
public class FeignClientAPITest {

    @Autowired
    private FeignClientAPI feignClientAPI;

    @Value("${wiremock.server.port}") int port;

    @Test
    void deveConsumirOServico() {

        SecurityContextHolder.getContext().setAuthentication(new JwtAuthenticationToken(
                TestSecurityConfig.jwt(), List.of()
        ));

        feignClientAPI.setApiGateway("http://localhost:"+port);

        // Stubbing WireMock
        stubFor(get(urlEqualTo("/dummy")).willReturn(aResponse()
                .withHeader("Content-Type", "application/json").withJsonBody(new TextNode("Hello World!"))));

        FakeAPI client = feignClientAPI.createClient(FakeAPI.class);
        assertEquals("Hello World!", client.dummyCall());
    }

    public interface FakeAPI {

        @Headers("Content-Type: application/json")
        @RequestLine("GET /dummy")
        Object dummyCall();

    }
}
