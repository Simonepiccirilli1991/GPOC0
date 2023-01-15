package com.gwpoc.client;

import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import com.gwpoc.fragment.iwdb.UtenteIwResponse;
import com.gwpoc.model.request.UtenteRequest;

import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@WebFluxTest(IwdbClient.class)
public class ClientTest {

    @Autowired
    private WebTestClient webTestClient;
    
    @MockBean
    private IwdbClient iwdbClient;
    
    private UtenteRequest request;
    private UtenteIwResponse response;
    
    @BeforeEach
    public void setUp() {
        request = new UtenteRequest();
        response = new UtenteIwResponse();
    }
    
    @Test
    public void testRegistraUt() {
        // given
        when(iwdbClient.registraUt(request)).thenReturn(response);
        
        // when
        webTestClient.post()
                .uri("/prodotti/transact")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), UtenteRequest.class)
                .exchange()
                
        // then
                .expectStatus().isOk()
                .expectBody(UtenteIwResponse.class)
                .isEqualTo(response);
    }
    
    @Test
    public void testUpdateUtente() {
        // given
        when(iwdbClient.updateUtente(request)).thenReturn(response);
        
        // when
        webTestClient.post()
                .uri("/ut/update")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), UtenteRequest.class)
                .exchange()
                
        // then
                .expectStatus().isOk()
                .expectBody(UtenteIwResponse.class)
                .isEqualTo(response);
    }
    
    @Test
    public void testGetUtente() {
        // given
        String bt = "123";
        when(iwdbClient.getUtente(bt)).thenReturn(response);
        
        // when
        webTestClient.get()
                .uri("/ut/get/{bt}", bt)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                
        // then
                .expectStatus().isOk()
                .expectBody(UtenteIwResponse.class)
                .isEqualTo(response);
    }
}
