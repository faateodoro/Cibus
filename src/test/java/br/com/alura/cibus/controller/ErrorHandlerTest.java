package br.com.alura.cibus.controller;

import br.com.alura.cibus.repository.CozinhaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URI;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ActiveProfiles("test")
public class ErrorHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CozinhaRepository cozinhaRepository;

    @Test
    public void deve_retornar_pagina_not_found_caso_url_nao_exista() throws Exception {
        URI uri = new URI("/esse-endereco-nao-existe");
        this.mockMvc.perform(get(uri))
                .andExpect(status().is(404));
    }
}