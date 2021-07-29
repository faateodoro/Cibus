package br.com.alura.cibus.controller;

import br.com.alura.cibus.controller.dto.CozinhaForm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
public class CozinhaControllerTest {
	
	@Autowired
	private MockMvc mock;

	@Test
	public void deveriaRetornar200CasoTenhaCozinhasCadastradas() throws Exception {
		URI uri = new URI("/cozinhas");
		this.mock.perform(MockMvcRequestBuilders.get(uri))
			.andExpect(MockMvcResultMatchers.status().is(200));
	}
	
	@Test
	public void deveriaSalvarNovaCozinhaCasoONomeNaoSejaDuplicado() throws Exception {
		URI uri = new URI("/admin/cozinhas/salvar");
		CozinhaForm form = new CozinhaForm("Mineira");
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(form);
		this.mock.perform(MockMvcRequestBuilders.post(uri).content(json))
			.andExpect(MockMvcResultMatchers.status().is(200));
	}

}
