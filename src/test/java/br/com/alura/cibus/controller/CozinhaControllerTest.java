package br.com.alura.cibus.controller;

import br.com.alura.cibus.modelo.Cozinha;
import br.com.alura.cibus.repository.CozinhaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import static java.util.Optional.ofNullable;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CozinhaController.class)
@ActiveProfiles("test")
public class CozinhaControllerTest {

	@Autowired
	private MockMvc mockMvc;

	private List<Cozinha> cozinhas;

	@MockBean
	private CozinhaRepository cozinhaRepository;
	private Cozinha cozinha;

	@BeforeEach
	public void before(){
		this.cozinhas = Arrays.asList(
				new Cozinha("Árabe"),
				new Cozinha("Baiana"),
				new Cozinha("Chinesa"),
				new Cozinha("Italiana")
		);

		cozinha = cozinhas.get(0);

		when(this.cozinhaRepository.findAll()).thenReturn(this.cozinhas);
		when(this.cozinhaRepository.findByNome("Árabe")).thenReturn(ofNullable(this.cozinha));
		when(this.cozinhaRepository.findByOrderByNome()).thenReturn(this.cozinhas);
		when(this.cozinhaRepository.findById(1L)).thenReturn(ofNullable(this.cozinha));
	}

	@Test
	public void deveria_retornar_200_caso_tenha_cozinhas_cadastradas() throws Exception {
		URI uri = new URI("/cozinhas");
		this.mockMvc.perform(get(uri))
				.andExpect(view().name("cozinhas/listagem"))
				.andExpect(model().attribute("listaCozinhas", this.cozinhas))
				.andExpect(status().is(200));
	}

	@Test
	public void deveria_salvar_nova_cozinha_caso_o_nome_nao_seja_duplicado() throws Exception {
		URI uri = new URI("/admin/cozinhas/salvar");

		this.mockMvc.perform(post(uri).contentType(MediaType.APPLICATION_FORM_URLENCODED).param("nome", "Mineira"))
				.andExpect(header().stringValues("Location", "/cozinhas"))
				.andExpect(status().is3xxRedirection());

		verify(this.cozinhaRepository).save(any(Cozinha.class));
	}

	@Test
	public void deveria_dar_erro_caso_nome_seja_vazio() throws Exception {
		URI uri = new URI("/admin/cozinhas/salvar");

		this.mockMvc.perform(post(uri).contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.param("nome", ""))
				.andExpect(view().name("admin/cozinhas/adicionar"))
				.andExpect(status().isOk());

		verify(this.cozinhaRepository, never()).save(any(Cozinha.class));
	}

	@Test
	public void deveria_dar_erro_caso_nome_seja_nulo() throws Exception {
		URI uri = new URI("/admin/cozinhas/salvar");

		this.mockMvc.perform(post(uri).contentType(MediaType.APPLICATION_FORM_URLENCODED))
				.andExpect(view().name("admin/cozinhas/adicionar"))
				.andExpect(status().isOk());

		verify(this.cozinhaRepository, never()).save(any(Cozinha.class));
	}

	@Test
	public void deveria_dar_erro_caso_nome_tenha_mais_que_o_maximo_de_caracteres() throws Exception {
		URI uri = new URI("/admin/cozinhas/salvar");

		this.mockMvc.perform(post(uri).contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.param("nome", "Este nome de cozinha deve exceder o máximo de caracteres permitidos"))
				.andExpect(view().name("admin/cozinhas/adicionar"))
				.andExpect(status().isOk());

		verify(this.cozinhaRepository, never()).save(any(Cozinha.class));
	}

	@Test
	public void deveria_alterar_cozinha_caso_o_nome_nao_seja_duplicado() throws Exception {
		URI uri = new URI("/admin/cozinhas/salvar/1");
		this.mockMvc.perform(post(uri).contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.param("nome", "Mineira"))
				.andExpect(header().stringValues("Location", "/cozinhas"))
				.andExpect(status().is3xxRedirection());

		verify(this.cozinhaRepository).findById(1L);
		verify(this.cozinhaRepository).save(cozinha);
	}

	@Test
	public void deveria_retornar_chamar_pagina_de_editar_cozinha() throws Exception {
		URI uri = new URI("/admin/cozinhas/editar/1");
		this.mockMvc.perform(get(uri))
				.andExpect(view().name("admin/cozinhas/editar"))
				.andExpect(model().attribute("cozinha", this.cozinha))
				.andExpect(status().isOk());

		verify(this.cozinhaRepository).findById(1L);
	}

	@Test
	public void deveria_chamar_pagina_de_erro_caso_tente_editar_id_inexistente() throws Exception{
		URI uri = new URI("/admin/cozinhas/editar/1687835463");
		this.mockMvc.perform(get(uri))
				.andExpect(result -> assertTrue(result
						.getResolvedException() instanceof  ResponseStatusException))
				.andExpect(status().is(404));
	}

	@Test
	public void deveria_deletar_uma_cozinha_caso_id_exista() throws Exception{
		URI uri = new URI("/admin/cozinhas/excluir");
		this.mockMvc.perform(post(uri).contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.param("id", "1"))
				.andExpect(header().stringValues("Location", "/cozinhas"))
				.andExpect(status().is3xxRedirection());

		verify(this.cozinhaRepository).findById(1L);
		verify(this.cozinhaRepository).delete(this.cozinha);
	}

	@Test
	public void deveria_redirecionar_para_lista_de_cozinhas_caso_tente_deletar_id_inexistente() throws Exception{
		URI uri = new URI("/admin/cozinhas/excluir");
		this.mockMvc.perform(post(uri).contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.param("id", "32168746514684"))
				.andExpect(header().stringValues("Location", "/cozinhas"))
				.andExpect(status().is3xxRedirection());

		verify(this.cozinhaRepository, never()).delete(any(Cozinha.class));
	}

}